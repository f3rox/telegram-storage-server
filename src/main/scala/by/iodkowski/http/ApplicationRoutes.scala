package by.iodkowski.http

import java.util.UUID

import by.iodkowski.auth.{AuthRoutes, AuthService, JwtAuthMiddleware}
import by.iodkowski.file.{FileRoutes, FileService}
import by.iodkowski.hello.{HelloRoutes, HelloService}
import by.iodkowski.user.{UserRoutes, UserService}
import by.iodkowski.utils.Security
import cats.Parallel
import cats.effect.Concurrent
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import org.http4s.server.middleware._

object ApplicationRoutes {

  val V1: String = "v1"

  def of[F[_]: Concurrent: Parallel](
    helloService: HelloService[F],
    fileService: FileService[F],
    userService: UserService[F],
    authService: AuthService[F],
    security: Security[F]
  ): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    val authMiddleware =
      JwtAuthMiddleware
        .of[F, UUID](
          security.userJwtAuth,
          authService.auth,
          onError = Forbidden(_)
        )

    val apiRoutes =
      HelloRoutes.of(helloService) <+>
        UserRoutes.of(userService) <+>
        FileRoutes.of(fileService, authMiddleware) <+>
        AuthRoutes.of(authService, authMiddleware)

    Router("/api" -> Logger.httpRoutes(logHeaders = true, logBody = true)(CORS(apiRoutes)))
  }
}
