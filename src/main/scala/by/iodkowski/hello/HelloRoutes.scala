package by.iodkowski.hello

import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object HelloRoutes {
  def of[F[_]: Sync](helloService: HelloService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root => helloService.hello().flatMap(Ok(_))
    }
  }
}
