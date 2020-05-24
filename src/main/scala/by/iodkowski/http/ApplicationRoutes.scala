package by.iodkowski.http

import by.iodkowski.hello.{HelloRoutes, HelloService}
import cats.effect.Sync
import org.http4s.HttpRoutes
import org.http4s.server.Router

object ApplicationRoutes {
  def of[F[_]: Sync](helloService: HelloService[F]): HttpRoutes[F] =
    Router[F](
      "/hello" -> HelloRoutes.of(helloService)
    )
}
