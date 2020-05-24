package by.iodkowski.http

import by.iodkowski.config.AppConfig.HttpConfig
import cats.effect.{ConcurrentEffect, Timer}
import org.http4s.HttpRoutes
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext

object HttpServer {
  def start[F[_]: ConcurrentEffect: Timer](config: HttpConfig, routes: HttpRoutes[F]): F[Unit] =
    BlazeServerBuilder[F](ExecutionContext.global)
      .bindHttp(config.port, config.host)
      .withHttpApp(routes.orNotFound)
      .serve
      .compile
      .drain
}
