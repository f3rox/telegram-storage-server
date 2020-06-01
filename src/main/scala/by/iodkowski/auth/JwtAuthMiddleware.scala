package by.iodkowski.auth

import java.util.UUID

import cats.MonadError
import cats.data.{Kleisli, OptionT}
import cats.implicits._
import dev.profunktor.auth.jwt._
import io.circe.derivation.annotations.JsonCodec
import org.http4s.server.AuthMiddleware
import org.http4s.{AuthedRoutes, Request, Response}
import pdi.jwt._
import pdi.jwt.exceptions.JwtException

object JwtAuthMiddleware {
  type MonadThrow[F[_]] = MonadError[F, Throwable]
  def of[F[_]: MonadThrow, A](
    jwtAuth: JwtAuth,
    authenticate: JwtToken => JwtClaim => F[Option[A]],
    onError: String => F[Response[F]]
  ): AuthMiddleware[F, A] = {
    val authUser: Kleisli[F, Request[F], Either[String, A]] =
      Kleisli { req =>
        req.cookies
          .find(_.name == "token")
          .map(cookie => JwtToken(cookie.content))
          .fold("Token not found".asLeft[A].pure[F]) { token =>
            jwtDecode[F](token, jwtAuth)
              .flatMap(authenticate(token))
              .map(_.fold("Not authorized".asLeft[A])(_.asRight[String]))
              .recover {
                case _: JwtException => "Invalid token".asLeft[A]
              }
          }
      }
    val onFailure: AuthedRoutes[String, F] = Kleisli(req => OptionT.liftF(onError(req.context)))
    AuthMiddleware(authUser, onFailure)
  }
  @JsonCodec
  final case class JwtUser(id: UUID)
}
