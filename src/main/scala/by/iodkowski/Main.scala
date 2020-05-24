package by.iodkowski

import by.iodkowski.config.AppConfig
import by.iodkowski.hello.HelloService
import by.iodkowski.http.{ApplicationRoutes, HttpServer}
import cats.effect.{ConcurrentEffect, ExitCode, IO, IOApp, Timer}
import cats.implicits._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = runF[IO]

  private def runF[F[_]: ConcurrentEffect: Timer]: F[ExitCode] =
    for {
      config       <- AppConfig.load
      helloService = HelloService.make
      routes       = ApplicationRoutes.of(helloService)
      _            <- HttpServer.start(config.http, routes)
    } yield ExitCode.Success
}
