package by.iodkowski.app

import by.iodkowski.app.AppConfig.{DbConfig, HttpConfig, SecurityConfig, TelegramConfig}
import cats.effect.Sync
import pureconfig.generic.semiauto._
import pureconfig.{ConfigReader, ConfigSource}

import scala.concurrent.duration.FiniteDuration

final case class AppConfig(http: HttpConfig, telegram: TelegramConfig, storageDb: DbConfig, security: SecurityConfig)

object AppConfig {

  final case class HttpConfig(host: String, port: Int)
  private implicit val httpConfigReader: ConfigReader[HttpConfig] = deriveReader

  final case class TelegramConfig(
    apiId: Int,
    apiHash: String,
    databaseDirectory: String,
    useMessageDatabase: Boolean,
    useSecretChats: Boolean,
    systemLanguageCode: String,
    deviceModel: String,
    systemVersion: String,
    applicationVersion: String,
    enableStorageOptimizer: Boolean,
    storageChatId: Long
  )
  private implicit val telegramConfigReader: ConfigReader[TelegramConfig] = deriveReader

  final case class DbConfig(host: String, port: Int, user: String, password: String, database: String, poolSize: Int)
  private implicit val dbConfigReader: ConfigReader[DbConfig] = deriveReader

  final case class SecurityConfig(tokenExpiresIn: FiniteDuration, jwtSecretKey: String)
  private implicit val securityConfigReader: ConfigReader[SecurityConfig] = deriveReader

  private implicit val configReader: ConfigReader[AppConfig] = deriveReader

  def load[F[_]: Sync]: F[AppConfig] = Sync[F].delay(ConfigSource.default.loadOrThrow[AppConfig])
}
