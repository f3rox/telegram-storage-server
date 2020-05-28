package by.iodkowski

import by.iodkowski.config.AppConfig
import by.iodkowski.hello.HelloService
import by.iodkowski.http.{ApplicationRoutes, HttpServer}
import by.iodkowski.telegram.{TdLib, TelegramService}
import cats.Parallel
import cats.effect.{ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Resource, Timer}
import cats.implicits._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = runF[IO]

  private def runF[F[_]: ConcurrentEffect: Timer: ContextShift: Parallel]: F[ExitCode] = {

    val dependencies = for {
      _               <- Resource.liftF(TdLib.loadLibrary)
      _               <- Resource.liftF(TdLib.setLogVerbosityLevel(1))
      config          <- Resource.liftF(AppConfig.load)
      helloService    = HelloService.create
      telegramService <- TelegramService.of(config.tdLib)
      routes          = ApplicationRoutes.of(helloService, telegramService)
    } yield (config.http, routes)

    dependencies.use {
      case (httpConfig, routes) =>
        HttpServer.start(httpConfig, routes) as ExitCode.Success
    }
  }
}
