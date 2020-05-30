package by.iodkowski.utils

import java.nio.file.{Files, Paths}
import java.util.UUID

import cats.effect.Sync

object common {

  def randomUUID[F[_]: Sync]: F[UUID] = Sync[F].delay(java.util.UUID.randomUUID())

  def deleteFileIfExists[F[_]: Sync](localPath: String): F[Boolean] =
    Sync[F].delay(Files.deleteIfExists(Paths.get(localPath)))
}
