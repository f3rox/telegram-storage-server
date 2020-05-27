package by.iodkowski.config

import by.iodkowski.config.AppConfig.{HttpConfig, TdLibParameters}
import cats.effect.Sync
import pureconfig.generic.semiauto._
import pureconfig.{ConfigReader, ConfigSource}

final case class AppConfig(http: HttpConfig, tdLib: TdLibParameters)

object AppConfig {

  final case class HttpConfig(host: String, port: Int)
  object HttpConfig {
    implicit val configReader: ConfigReader[HttpConfig] = deriveReader
  }

  final case class TdLibParameters(
    apiId: Int,
    apiHash: String,
    databaseDirectory: String,
    useMessageDatabase: Boolean,
    useSecretChats: Boolean,
    systemLanguageCode: String,
    deviceModel: String,
    systemVersion: String,
    applicationVersion: String,
    enableStorageOptimizer: Boolean
  )
  object TdLibParameters {
    implicit val configReader: ConfigReader[TdLibParameters] = deriveReader
  }

  implicit val configReader: ConfigReader[AppConfig] = deriveReader

  def load[F[_]: Sync]: F[AppConfig] = Sync[F].delay(ConfigSource.default.loadOrThrow[AppConfig])
}
