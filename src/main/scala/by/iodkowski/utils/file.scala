package by.iodkowski.utils

import java.nio.file.{Files, Paths}

import cats.effect.Sync

object file {
  def deleteFileIfExists[F[_]: Sync](localPath: String): F[Boolean] =
    Sync[F].delay(Files.deleteIfExists(Paths.get(localPath)))
}
