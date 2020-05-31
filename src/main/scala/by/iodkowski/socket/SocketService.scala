package by.iodkowski.socket

import by.iodkowski.socket.SocketProtocol.ClientMessage._
import by.iodkowski.socket.SocketProtocol.ServerMessage._
import by.iodkowski.socket.SocketProtocol.{ClientMessage, ServerMessage}
import by.iodkowski.telegram.api.auth._
import by.iodkowski.telegram.api.{Client, UpdateAuthorizationState}
import by.iodkowski.utils.http4s._
import cats.effect.Concurrent
import cats.implicits._
import io.circe.parser._
import io.circe.syntax._
import fs2._
import fs2.concurrent.Queue
import org.http4s.websocket.WebSocketFrame

trait SocketService[F[_]] {
  def toClient: Stream[F, WebSocketFrame]
  def fromClient: Pipe[F, WebSocketFrame, Unit]
}

object SocketService {

  def of[F[_]: Concurrent](telegram: Client[F]): F[SocketService[F]] =
    Queue.unbounded[F, ServerMessage].map { serverResponses =>
      new SocketService[F] {

        val telegramUpdates: Stream[F, ServerMessage] = telegram.updates.collect {
          case UpdateAuthorizationState(authorizationState) =>
            authorizationState match {
              case _: AuthorizationStateWaitCode     => WaitCode
              case _: AuthorizationStateWaitPassword => WaitPassword
              case AuthorizationStateReady           => Ready
              case AuthorizationStateClosed          => Closed
              case _                                 => Error(s"Unsupported event: $authorizationState")
            }
        }

        val processClientMessage: ClientMessage => F[Unit] = {
          case CheckCode(code)         => telegram.checkAuthenticationCode(code)
          case CheckPassword(password) => telegram.checkAuthenticationPassword(password)
          case LogOut                  => telegram.logOut
        }

        def toClient: Stream[F, WebSocketFrame] =
          telegramUpdates.merge(serverResponses.dequeue).map(message => WebSocketFrame.fromJson(message.asJson))

        def fromClient: Pipe[F, WebSocketFrame, Unit] = _.evalMap {
          case text: WebSocketFrame.Text =>
            println(text)
            decode[ClientMessage](text.str)
              .fold(error => serverResponses.enqueue1(Error(error.getMessage)), processClientMessage)
        }
      }
    }
}
