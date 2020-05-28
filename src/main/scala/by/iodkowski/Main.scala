package by.iodkowski

import by.iodkowski.config.AppConfig
import by.iodkowski.hello.HelloService
import by.iodkowski.http.{ApplicationRoutes, HttpServer}
import by.iodkowski.telegram.{TdLib, TelegramClient}
import cats.effect.{ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Sync, Timer}
import cats.implicits._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = runF[IO]

  private def runF[F[_]: ConcurrentEffect: Timer: ContextShift]: F[ExitCode] =
    for {
      _            <- TdLib.loadLibrary
      _            <- TdLib.setLogVerbosityLevel(2)
      config       <- AppConfig.load
      helloService = HelloService.create
      routes       = ApplicationRoutes.of(helloService)
      _            <- TelegramClient.of(config.tdLib)
      _            <- HttpServer.start(config.http, routes)
    } yield ExitCode.Success
}
