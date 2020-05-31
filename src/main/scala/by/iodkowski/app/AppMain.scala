package by.iodkowski.app

import by.iodkowski.auth.AuthService
import by.iodkowski.db.DbSessionPool
import by.iodkowski.file.FileService
import by.iodkowski.hello.HelloService
import by.iodkowski.http.{ApplicationRoutes, HttpServer}
import by.iodkowski.telegram.api.Client
import by.iodkowski.telegram.{TdLib, TelegramClient}
import by.iodkowski.user.UserService
import by.iodkowski.utils.Security
import cats.Parallel
import cats.effect.{ConcurrentEffect, ContextShift, ExitCode, IO, IOApp, Resource, Timer}
import cats.implicits._
import natchez.Trace
import natchez.Trace.Implicits.noop

object AppMain extends IOApp {
  def run(args: List[String]): IO[ExitCode] = runF[IO]

  private def runF[F[_]: ConcurrentEffect: Timer: ContextShift: Parallel: Trace]: F[ExitCode] = {
    val resources = for {
      _              <- Resource.liftF(TdLib.loadLibrary)
      _              <- Resource.liftF(TdLib.setLogVerbosityLevel(2))
      config         <- Resource.liftF(AppConfig.load)
      helloService   = HelloService.create
      apiClient      <- Resource.liftF(Client.create)
      telegramClient = TelegramClient.of(apiClient, 16062574505L, config.telegram)
      security       = Security.of(config.security)
      fileService    <- FileService.of(telegramClient, security)
      dbSessionPool  <- DbSessionPool.of(config.storageDb)
      userService    <- Resource.liftF(UserService.of(dbSessionPool, security))
      authService    <- AuthService.of(userService, security)
      routes         = ApplicationRoutes.of(helloService, fileService, userService, authService, security)
    } yield (config.http, routes, telegramClient, fileService)

    resources.use {
      case (httpConfig, routes, telegramClient, fileService) =>
        HttpServer
          .stream(httpConfig, routes)
          // .merge(telegramClient.updates.through(fileService.processFiles))
          .compile
          .drain as ExitCode.Success
    }
  }
}
