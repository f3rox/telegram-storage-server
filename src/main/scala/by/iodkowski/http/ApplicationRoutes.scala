package by.iodkowski.http

import by.iodkowski.auth.AuthSocket
import by.iodkowski.hello.{HelloRoutes, HelloService}
import cats.effect.Concurrent
import org.http4s.HttpRoutes
import org.http4s.server.Router

object ApplicationRoutes {
  def of[F[_]: Concurrent](helloService: HelloService[F]): HttpRoutes[F] =
    Router[F](
      "/hello" -> HelloRoutes.of(helloService),
      "/auth"  -> AuthSocket.of[F]
    )
}
