package by.iodkowski.file

import java.nio.file.{Path, Paths}
import java.util.UUID

import by.iodkowski.file.FileQueries._
import by.iodkowski.telegram.TelegramClient
import by.iodkowski.telegram.api.{Update, UpdateFile}
import by.iodkowski.utils.Security
import by.iodkowski.utils.file._
import cats.effect.concurrent.Ref
import cats.effect.{Blocker, ContextShift, Resource, Sync}
import cats.implicits._
import fs2._
import fs2.io.file
import org.http4s.multipart.Part
import skunk.{Session, SqlState}

import scala.collection.immutable.{HashMap, HashSet}
import scala.util.control.NoStackTrace

trait FileService[F[_]] {
  def processFiles: Pipe[F, Update, Unit]
  def upload(userId: UUID, part: Part[F]): F[Unit]
}

object FileService {

  def of[F[_]: Sync: ContextShift](
    telegramClient: TelegramClient[F],
    sessionPool: Resource[F, Session[F]],
    security: Security[F]
  ): Resource[F, FileService[F]] =
    for {
      blocker        <- Blocker[F]
      uploadingFiles <- Resource.liftF(Ref.of[F, HashMap[UUID, HashSet[Int]]](HashMap.empty))
    } yield new FileService[F] {

      def processFiles: Pipe[F, Update, Unit] =
        _.evalTap {
          case UpdateFile(file) =>
            Sync[F].delay(
              println(
                f"FILE_ID: ${file.id}, PROGRESS: ${(file.remote.uploadedSize.toDouble / file.local.downloadedSize.toDouble) * 100.0}%.2f%%"
              )
            )
          case _ => ().pure[F]
        }.evalMap {
          case UpdateFile(file) if file.remote.isUploadingCompleted =>
            uploadingFiles.get.flatMap { files =>
              files.find(_._2.contains(file.id)).foldMapA {
                case (userId, _) =>
                  for {
                    _         <- deleteFileIfExists(file.local.path)
                    localPath = file.local.path
                    fileName  = localPath.substring(localPath.lastIndexOf("/") + 1)
                    _         <- saveToDb(UploadedFile(file.remote.uniqueId, userId, fileName))
                    _ <- uploadingFiles.update { files =>
                      files
                        .get(userId)
                        .fold(files) { fileIds =>
                          (fileIds - file.id).some
                            .filterNot(_.isEmpty)
                            .fold(files.removed(userId))(files.updated(userId, _))
                        }
                    }
                    _ <- Sync[F]
                      .delay(println(s"  SUCCESSFULLY UPLOADED: USER_ID: $userId, FILE_ID: ${file.remote.uniqueId}"))
                  } yield ()
              }
            }
          case _ => ().pure[F]
        }

      def upload(userId: UUID, part: Part[F]): F[Unit] =
        for {
          tmpFileName <- part.filename.fold(security.randomUUID.map(uuid => s"$uuid.mp3"))(_.pure[F])
          tmpFilePath = Paths.get(s"tmp/$tmpFileName")
          _           = println(tmpFilePath)
          _           <- writeToFile(part.body, tmpFilePath)
          fileId      <- telegramClient.sendAudioFile(tmpFilePath)
          _           = println(fileId)
          _           <- uploadingFiles.getAndUpdate(map => map.updated(userId, map.getOrElse(userId, HashSet.empty) + fileId))
          _           <- uploadingFiles.get.flatMap(x => Sync[F].delay(println(x)))
        } yield ()

      private def saveToDb(file: UploadedFile): F[Unit] =
        sessionPool.use { session =>
          session.prepare(insertFile).use { cmd =>
            cmd
              .execute(file)
              .void
              .handleErrorWith {
                case SqlState.UniqueViolation(_) =>
                  FileAlreadyUploaded.raiseError[F, Unit] // TODO: Return existing file name
              }
          }
        }

      private def writeToFile(stream: Stream[F, Byte], path: Path): F[Unit] =
        stream.through(file.writeAll(path, blocker)).compile.drain
    }

  final case object FileAlreadyUploaded extends NoStackTrace
}
