package by.iodkowski.db

import by.iodkowski.app.AppConfig.DbConfig
import cats.effect.{Concurrent, ContextShift}
import cats.implicits._
import natchez.Trace
import skunk.{Session, SessionPool}

object DbSessionPool {
  def of[F[_]: Concurrent: ContextShift: Trace](config: DbConfig): SessionPool[F] =
    Session.pooled(
      host     = config.host,
      port     = config.port,
      user     = config.user,
      password = config.password.some,
      database = config.database,
      max      = config.poolSize
    )
}
