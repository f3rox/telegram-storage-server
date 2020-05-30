package by.iodkowski.telegram

import by.iodkowski.app.AppConfig.TdLibConfig
import by.iodkowski.telegram.api._
import by.iodkowski.telegram.api.auth._
import cats.effect.{ConcurrentEffect, IO}
import cats.implicits._
import fs2.concurrent._
import fs2.{Pipe, Stream}
import org.drinkless.tdlib.{Client, TdApi}

import scala.io.StdIn

trait TelegramClient[F[_]] {
  def updates: Stream[F, Update]
  def setTdLibParameters(parameters: TdLibConfig): F[Unit]
  def checkDatabaseEncryptionKey: F[Unit]
  def checkAuthenticationCode(code: String): F[Unit]
  def setAuthenticationPhoneNumber(
    phoneNumber: Long,
    settings: Option[PhoneNumberAuthenticationSettings] = None
  ): F[Unit]
  def requestQrCodeAuthentication(otherUserIds: List[Int] = List.empty): F[Unit]
  def checkAuthenticationPassword(password: String): F[Unit]
  def logOut: F[Unit]
}

object TelegramClient {

  def of[F[_]](phoneNumber: Long, tdLibConfig: TdLibConfig)(implicit F: ConcurrentEffect[F]): F[TelegramClient[F]] =
    for {
      q <- Queue.unbounded[F, Update]
      jClient <- F.delay {
        def enqueue(update: TdApi.Object): Unit =
          F.runAsync(q.enqueue1(Update.fromJava(update)))(_ => IO.unit).unsafeRunSync()
        Client.create(enqueue, println, println)
      }
    } yield new TelegramClient[F] {

      private def updatesPreprocessor: Pipe[F, Update, Update] =
        _.evalTap(upd => F.delay(println(upd)))
          .evalTap {
            case UpdateAuthorizationState(authorizationState) =>
              authorizationState match {
                case AuthorizationStateWaitTdlibParameters  => setTdLibParameters(tdLibConfig)
                case _: AuthorizationStateWaitEncryptionKey => checkDatabaseEncryptionKey
                case AuthorizationStateWaitPhoneNumber      => setAuthenticationPhoneNumber(phoneNumber)
                case _: AuthorizationStateWaitCode =>
                  F.delay(StdIn.readLine("Enter code: ")).flatMap(checkAuthenticationCode)
                case _: AuthorizationStateWaitPassword =>
                  F.delay(StdIn.readLine("Enter password: ")).flatMap(checkAuthenticationPassword)
                case _ => F.unit
              }
            case _ => F.unit
          }

      private def resultHandler(result: TdApi.Object): Unit = println(result)

      override def updates: Stream[F, Update] = q.dequeue.through(updatesPreprocessor)

      override def setTdLibParameters(parameters: TdLibConfig): F[Unit] = F.delay {
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
        jClient.send(new TdApi.SetTdlibParameters(params), resultHandler)
      }

      override def checkDatabaseEncryptionKey: F[Unit] =
        F.delay(jClient.send(new TdApi.CheckDatabaseEncryptionKey(), resultHandler))

      override def checkAuthenticationCode(code: String): F[Unit] =
        F.delay(jClient.send(new TdApi.CheckAuthenticationCode(code), resultHandler))

      override def setAuthenticationPhoneNumber(
        phoneNumber: Long,
        settings: Option[PhoneNumberAuthenticationSettings]
      ): F[Unit] =
        F.delay(
          jClient
            .send(
              new TdApi.SetAuthenticationPhoneNumber(phoneNumber.toString, settings.map(_.toJava).orNull),
              resultHandler
            )
        )

      override def requestQrCodeAuthentication(otherUserIds: List[Int]): F[Unit] =
        F.delay(
          jClient
            .send(
              new TdApi.RequestQrCodeAuthentication(Option.when(otherUserIds.nonEmpty)(otherUserIds.toArray).orNull),
              resultHandler
            )
        )

      override def checkAuthenticationPassword(password: String): F[Unit] =
        F.delay(jClient.send(new TdApi.CheckAuthenticationPassword(password), resultHandler))

      override def logOut: F[Unit] = F.delay(jClient.send(new TdApi.LogOut(), resultHandler))
    }
}
