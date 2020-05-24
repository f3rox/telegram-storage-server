package by.iodkowski.config

import by.iodkowski.config.AppConfig.HttpConfig
import cats.effect.Sync
import pureconfig.generic.semiauto._
import pureconfig.{ConfigReader, ConfigSource}

final case class AppConfig(http: HttpConfig)

object AppConfig {

  def load[F[_]: Sync]: F[AppConfig] = Sync[F].delay(ConfigSource.default.loadOrThrow[AppConfig])

  final case class HttpConfig(host: String, port: Int)
  object HttpConfig {
    implicit val configReader: ConfigReader[HttpConfig] = deriveReader
  }

  implicit val configReader: ConfigReader[AppConfig] = deriveReader
}
