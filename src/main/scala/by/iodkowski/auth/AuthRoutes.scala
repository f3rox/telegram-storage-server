package by.iodkowski.auth

import java.util.UUID

import by.iodkowski.auth.AuthService.InvalidUsernameOrPassword
import by.iodkowski.http.ApplicationRoutes.V1
import cats.effect.Sync
import cats.implicits._
import dev.profunktor.auth.jwt.JwtToken
import io.circe.derivation.annotations.{Configuration, JsonCodec}
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}
import org.http4s.{AuthedRoutes, HttpRoutes}

object AuthRoutes {
  def of[F[_]: Sync](authService: AuthService[F], authMiddleware: AuthMiddleware[F, UUID]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    val login =
      HttpRoutes.of[F] {
        case req @ POST -> Root =>
          req.decode[LoginUser] { user =>
            authService
              .login(user.username, user.password)
              .flatMap(token => Ok().map(_.addCookie("token", token.value)))
              .recoverWith {
                case InvalidUsernameOrPassword => BadRequest("Invalid username or password")
              }
          }
      }
    val logout =
      authMiddleware {
        AuthedRoutes.of[UUID, F] {
          case req @ POST -> Root as _ =>
            req.req.cookies
              .find(_.name == "token")
              .map(value => JwtToken(value.content))
              .traverse_(authService.logout) *>
              Ok().map(_.removeCookie("token"))
        }
      }

    Router(
      s"$V1/login"  -> login,
      s"$V1/logout" -> logout
    )
  }
  @JsonCodec(Configuration.decodeOnly)
  final case class LoginUser(username: String, password: String)
}
