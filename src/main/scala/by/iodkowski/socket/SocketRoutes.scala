package by.iodkowski.socket

import by.iodkowski.http.ApplicationRoutes.V1
import by.iodkowski.telegram.TelegramService
import cats.effect.Concurrent
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.websocket.WebSocketBuilder

object SocketRoutes {
  def of[F[_]: Concurrent](telegramService: TelegramService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / V1 / "socket" / LongVar(phoneNumber) =>
        for {
          telegramClient <- telegramService.client(phoneNumber)
          socketService  <- SocketService.of(telegramClient)
          webSocket      <- WebSocketBuilder[F].build(socketService.toClient, socketService.fromClient)
        } yield webSocket
    }
  }
}
