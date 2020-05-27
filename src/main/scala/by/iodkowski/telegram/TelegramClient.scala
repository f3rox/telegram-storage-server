package by.iodkowski.telegram

import by.iodkowski.config.AppConfig.TdLibParameters
import cats.effect.{ConcurrentEffect, Effect, IO, Sync}
import cats.implicits._
import fs2.Stream
import fs2.concurrent._
import org.drinkless.tdlib.TdApi.SetTdlibParameters
import org.drinkless.tdlib.{Client, TdApi}

trait TelegramClient[F[_]] {
  def updates: Stream[F, TdApi.Object]
  def setTdLibParameters(parameters: TdLibParameters): F[Unit]
}

object TelegramClient {

  def create[F[_]: ConcurrentEffect]: F[TelegramClient[F]] =
    for {
      q <- Queue.unbounded[F, TdApi.Object]
      client <- Sync[F].delay {
        def enqueue(update: TdApi.Object): Unit =
          Effect[F].runAsync(q.enqueue1(update))(_ => IO.unit).unsafeRunSync()
        Client.create(enqueue, println, println)
      }
    } yield new TelegramClient[F] {

      override def updates: Stream[F, TdApi.Object] = q.dequeue

      override def setTdLibParameters(parameters: TdLibParameters): F[Unit] = Sync[F].delay {
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
        client.send(new SetTdlibParameters(params), println)
      }
    }
}
