package by.iodkowski.http

import by.iodkowski.hello.{HelloRoutes, HelloService}
import by.iodkowski.socket.SocketRoutes
import by.iodkowski.telegram.TelegramService
import cats.effect.Concurrent
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.server.Router

object ApplicationRoutes {

  val V1: String = "v1"

  def of[F[_]: Concurrent](helloService: HelloService[F] /*, telegramService: TelegramService[F]*/ ): HttpRoutes[F] = {
    val apiRoutes = HelloRoutes.of(helloService) // <+> SocketRoutes.of(telegramService)
    Router("/api" -> apiRoutes)
  }
}
