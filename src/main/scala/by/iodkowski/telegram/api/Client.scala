package by.iodkowski.telegram.api

import by.iodkowski.app.AppConfig.TelegramConfig
import by.iodkowski.telegram.api.auth._
import by.iodkowski.telegram.api.file.InputFile
import by.iodkowski.telegram.api.message.Message
import cats.effect.concurrent.Deferred
import cats.effect.{ConcurrentEffect, IO}
import cats.implicits._
import fs2.Stream
import fs2.concurrent._
import org.drinkless.tdlib
import org.drinkless.tdlib.TdApi

trait Client[F[_]] {
  def updates: Stream[F, Update]
  def setTdLibParameters(parameters: TelegramConfig): F[Unit]
  def checkDatabaseEncryptionKey: F[Unit]
  def checkAuthenticationCode(code: String): F[Unit]
  def setAuthenticationPhoneNumber(
    phoneNumber: Long,
    settings: Option[PhoneNumberAuthenticationSettings] = None
  ): F[Unit]
  def requestQrCodeAuthentication(otherUserIds: List[Int] = List.empty): F[Unit]
  def checkAuthenticationPassword(password: String): F[Unit]
  def logOut: F[Unit]
  def uploadFile(file: InputFile, fileType: TdApi.FileType, priority: Int): F[Unit]
  def sendMessage(chatId: Long, inputMessageContent: TdApi.InputMessageContent): F[Message]
}

object Client {

  def create[F[_]](implicit F: ConcurrentEffect[F]): F[Client[F]] =
    for {
      q <- Queue.unbounded[F, Update]
      jClient <- F.delay {
        def enqueue(update: TdApi.Object): Unit =
          F.runAsync(q.enqueue1(Update.fromJava(update)))(_ => IO.unit).unsafeRunSync()
        tdlib.Client.create(enqueue, println, println)
      }
    } yield new Client[F] {

      private def resultHandler(result: TdApi.Object): Unit = println(result)

      override def updates: Stream[F, Update] = q.dequeue

      override def setTdLibParameters(parameters: TelegramConfig): F[Unit] = F.delay {
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

      override def uploadFile(file: InputFile, fileType: TdApi.FileType, priority: Int): F[Unit] =
        F.delay(jClient.send(new TdApi.UploadFile(file.toJava, fileType, priority), resultHandler))

      override def sendMessage(chatId: Long, inputMessageContent: TdApi.InputMessageContent): F[Message] =
        for {
          _ <- F.delay {
            val getChats = new TdApi.GetChats()
            getChats.limit = 10
            jClient.send(getChats, println)
          }
          deferredMessage <- Deferred[F, Message]
          resultHandler: tdlib.Client.ResultHandler = (result: TdApi.Object) => {
            println(result)
            F.runAsync(deferredMessage.complete(Message.fromJava(result.asInstanceOf[TdApi.Message])))(_ => IO.unit)
              .unsafeRunSync()
          }
          _ <- F.delay {
            val sendMessage = new TdApi.SendMessage()
            sendMessage.chatId              = chatId
            sendMessage.inputMessageContent = inputMessageContent
            jClient.send(sendMessage, resultHandler)
          }
          message <- deferredMessage.get
        } yield message
    }
}
