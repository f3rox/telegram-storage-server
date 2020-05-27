package by.iodkowski.telegram

import by.iodkowski.config.AppConfig.TdLibConfig
import by.iodkowski.telegram.api._
import by.iodkowski.telegram.api.auth.{AuthorizationStateWaitEncryptionKey, AuthorizationStateWaitTdlibParameters}
import cats.effect.{ConcurrentEffect, Effect, IO, Sync}
import cats.implicits._
import fs2.concurrent._
import fs2.{Pipe, Stream}
import org.drinkless.tdlib.TdApi.{CheckDatabaseEncryptionKey, SetTdlibParameters}
import org.drinkless.tdlib.{Client, TdApi}

trait TelegramClient[F[_]] {
  def updates: Stream[F, Update]
  def setTdLibParameters(parameters: TdLibConfig): F[Unit]
  def checkDatabaseEncryptionKey: F[Unit]
}

object TelegramClient {

  def of[F[_]: ConcurrentEffect](tdLibConfig: TdLibConfig): F[TelegramClient[F]] =
    for {
      q <- Queue.unbounded[F, Update]
      jClient <- Sync[F].delay {
        def enqueue(update: TdApi.Object): Unit =
          Effect[F].runAsync(q.enqueue1(Update.fromJava(update)))(_ => IO.unit).unsafeRunSync()
        Client.create(enqueue, println, println)
      }
    } yield new TelegramClient[F] {

      private def updatesPreprocessor: Pipe[F, Update, Update] = _.evalTap {
        case UpdateAuthorizationState(authorizationState) =>
          authorizationState match {
            case AuthorizationStateWaitTdlibParameters  => setTdLibParameters(tdLibConfig)
            case _: AuthorizationStateWaitEncryptionKey => checkDatabaseEncryptionKey
            case _                                      => Sync[F].unit
          }
        case _ => Sync[F].unit
      }

      override def updates: Stream[F, Update] = q.dequeue.through(updatesPreprocessor)

      override def setTdLibParameters(parameters: TdLibConfig): F[Unit] = Sync[F].delay {
        import parameters._
        val params = new TdApi.TdlibParameters()
        params.apiId                  = apiId
        params.apiHash                = apiHash
        params.databaseDirectory      = databaseDirectory
        params.useMessageDatabase     = useMessageDatabase
        params.useSecretChats         = useSecretChats
        params.systemLanguageCode     = systemLanguageCode
        params.deviceModel            = deviceModel
        params.systemVersion          = systemVersion
        params.applicationVersion     = applicationVersion
        params.enableStorageOptimizer = enableStorageOptimizer
        jClient.send(new SetTdlibParameters(params), println)
      }
      override def checkDatabaseEncryptionKey: F[Unit] =
        Sync[F].delay(jClient.send(new CheckDatabaseEncryptionKey(), println))
    }
}
