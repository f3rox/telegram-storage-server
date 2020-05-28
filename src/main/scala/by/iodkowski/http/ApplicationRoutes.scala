package by.iodkowski.http

import by.iodkowski.hello.{HelloRoutes, HelloService}
import by.iodkowski.socket.SocketRoutes
import by.iodkowski.telegram.TelegramService
import cats.effect.Concurrent
import org.http4s.HttpRoutes
import org.http4s.server.Router

object ApplicationRoutes {
  def of[F[_]: Concurrent](helloService: HelloService[F], telegramService: TelegramService[F]): HttpRoutes[F] =
    Router[F](
      "/hello"  -> HelloRoutes.of(helloService),
      "/socket" -> SocketRoutes.of[F](telegramService)
    )
}
