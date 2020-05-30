package by.iodkowski.http

import by.iodkowski.app.AppConfig.HttpConfig
import cats.effect.{ConcurrentEffect, ExitCode, Timer}
import fs2._
import org.http4s.HttpRoutes
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext

object HttpServer {
  def stream[F[_]: ConcurrentEffect: Timer](config: HttpConfig, routes: HttpRoutes[F]): Stream[F, ExitCode] =
    BlazeServerBuilder[F](ExecutionContext.global)
      .bindHttp(config.port, config.host)
      .withHttpApp(routes.orNotFound)
      .serve
}
