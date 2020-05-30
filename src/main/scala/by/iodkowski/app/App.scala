package by.iodkowski.app

import by.iodkowski.hello.HelloService
import by.iodkowski.http.{ApplicationRoutes, HttpServer}
import by.iodkowski.telegram.{TdLib, TelegramClient}
import cats.Parallel
import cats.effect.{ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Timer}
import cats.implicits._

object App extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = runF[IO]

  private def runF[F[_]: ConcurrentEffect: Timer: ContextShift: Parallel]: F[ExitCode] =
    for {
      _            <- TdLib.loadLibrary
      _            <- TdLib.setLogVerbosityLevel(1)
      config       <- AppConfig.load
      helloService = HelloService.create
      tgClient     <- TelegramClient.of(16062574505L, config.tdLib)
      routes       = ApplicationRoutes.of(helloService)
      httpServer   = HttpServer.stream(config.http, routes)
      _            <- httpServer.merge(tgClient.updates).compile.drain
    } yield ExitCode.Success
}
