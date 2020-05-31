package by.iodkowski.telegram

import java.nio.file.Path

import by.iodkowski.app.AppConfig.TelegramConfig
import by.iodkowski.telegram.api._
import by.iodkowski.telegram.api.auth._
import by.iodkowski.telegram.api.message.MessageAudio
import cats.effect.Sync
import cats.implicits._
import fs2._
import org.drinkless.tdlib.TdApi

import scala.io.StdIn

trait TelegramClient[F[_]] {
  def updates: Stream[F, Update]
  def sendAudioFile(localPath: Path): F[Int]
}

object TelegramClient {
  def of[F[_]](client: api.Client[F], phoneNumber: Long, telegramConfig: TelegramConfig)(
    implicit F: Sync[F]
  ): TelegramClient[F] =
    new TelegramClient[F] {

      private def updatesPreprocessor: Pipe[F, Update, Update] =
        _.evalTap(upd => F.delay(println(s"TELEGRAM_CLIENT: $upd")))
          .evalFilter {
            case UpdateAuthorizationState(authorizationState) =>
              authorizationState match {
                case AuthorizationStateWaitTdlibParameters  => client.setTdLibParameters(telegramConfig) as false
                case _: AuthorizationStateWaitEncryptionKey => client.checkDatabaseEncryptionKey as false
                case AuthorizationStateWaitPhoneNumber      => client.setAuthenticationPhoneNumber(phoneNumber) as false
                case _: AuthorizationStateWaitCode =>
                  F.delay(StdIn.readLine("Enter code: ")).flatMap(client.checkAuthenticationCode) as true
                case _: AuthorizationStateWaitPassword =>
                  F.delay(StdIn.readLine("Enter password: ")).flatMap(client.checkAuthenticationPassword) as true
                case _ => true.pure[F]
              }
            case _: UpdateFile => true.pure[F]
            case _             => false.pure[F]
          }

      def updates: Stream[F, Update] = client.updates.through(updatesPreprocessor)

      def sendAudioFile(localPath: Path): F[Int] = F.defer {
        val inputMessageAudio = new TdApi.InputMessageAudio()
        val inputFile         = new TdApi.InputFileLocal(localPath.toString)
        inputMessageAudio.audio = inputFile
        client
          .sendMessage(telegramConfig.storageChatId, inputMessageAudio)
          .flatMap {
            _.content match {
              case MessageAudio(audio, _) => audio.audio.id.pure[F]
              case _                      => F.raiseError(new Exception("Unexpected message content"))
            }
          }
      }
    }
}
