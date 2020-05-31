package by.iodkowski.auth

import java.util.UUID

import by.iodkowski.http.ApplicationRoutes.V1
import cats.effect.Sync
import cats.implicits._
import dev.profunktor.auth.jwt.JwtToken
import io.circe.derivation.annotations.{Configuration, JsonCodec}
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.AuthMiddleware
import org.http4s.{AuthedRoutes, HttpRoutes}

object AuthRoutes {
  def of[F[_]: Sync](authService: AuthService[F], authMiddleware: AuthMiddleware[F, UUID]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case req @ POST -> Root / V1 / "login" =>
        req.decode[LoginUser] { user =>
          authService.login(user.username, user.password).flatMap(token => Ok().map(_.addCookie("token", token.value)))
        }
    } <+>
      authMiddleware {
        AuthedRoutes.of[UUID, F] {
          case req @ POST -> Root / V1 / "logout" as _ =>
            req.req.cookies
              .find(_.name == "token")
              .map(value => JwtToken(value.content))
              .traverse_(authService.logout) *> Ok()
        }
      }
  }

  @JsonCodec(Configuration.decodeOnly)
  final case class LoginUser(username: String, password: String)
}
