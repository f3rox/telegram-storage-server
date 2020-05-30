package by.iodkowski.file

import java.nio.file.{Path, Paths}
import java.util.UUID

import by.iodkowski.telegram.TelegramClient
import by.iodkowski.telegram.api.{Update, UpdateFile}
import by.iodkowski.utils.common._
import cats.effect.concurrent.Ref
import cats.effect.{Blocker, ContextShift, Resource, Sync}
import cats.implicits._
import fs2._
import fs2.io.file
import org.http4s.multipart.Part

import scala.collection.immutable.{HashMap, HashSet}

trait FileService[F[_]] {
  def processFiles: Pipe[F, Update, Unit]
  def upload(userId: UUID, part: Part[F]): F[Unit]
}

object FileService {

  def of[F[_]: Sync: ContextShift](
    telegramClient: TelegramClient[F]
  ): Resource[F, FileService[F]] =
    for {
      blocker        <- Blocker[F]
      uploadingFiles <- Resource.liftF(Ref.of[F, HashMap[UUID, HashSet[Int]]](HashMap.empty))
    } yield new FileService[F] {

      override def processFiles: Pipe[F, Update, Unit] =
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
            uploadingFiles.get.flatMap { map =>
              map.find(_._2.contains(file.id)).fold(().pure[F]) {
                case (userId, _) =>
                  // TODO: ADD METADATA DATABASE
                  deleteFileIfExists(file.local.path) *>
                    uploadingFiles
                      .getAndUpdate { map =>
                        map
                          .get(userId)
                          .fold(map)(set =>
                            (set - file.id).some
                              .filterNot(_.isEmpty)
                              .fold(map.removed(userId))(map.updated(userId, _))
                          )
                      }
                      .void
                      .flatMap(_ =>
                        Sync[F]
                          .delay(println(s"SUCCESSFULLY UPLOADED: USER_ID: $userId, FILE_ID: ${file.remote.uniqueId}"))
                      )
              }
            }
          case _ => ().pure[F]
        }

      override def upload(userId: UUID, part: Part[F]): F[Unit] =
        Sync[F].defer(for {
          tmpFileName <- part.filename.fold(randomUUID.map(uuid => s"$uuid.mp3"))(_.pure[F])
          tmpFilePath = Paths.get(s"tmp/$tmpFileName")
          _           = println(tmpFilePath)
          _           <- writeToFile(part.body, tmpFilePath)
          fileId      <- telegramClient.sendAudioFile(tmpFilePath)
          _           = println(fileId)
          _           <- uploadingFiles.getAndUpdate(map => map.updated(userId, map.getOrElse(userId, HashSet.empty) + fileId))
          _           <- uploadingFiles.get.flatMap(x => Sync[F].delay(println(x)))
        } yield ())

      private def writeToFile(stream: Stream[F, Byte], path: Path): F[Unit] =
        stream.through(file.writeAll(path, blocker)).compile.drain
    }
}
