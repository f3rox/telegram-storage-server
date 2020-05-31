package by.iodkowski.auth
import java.util.UUID

import by.iodkowski.user.UserService
import by.iodkowski.utils.Security
import cats.Parallel
import cats.effect.{Concurrent, Resource, Timer}
import cats.implicits._
import com.evolutiongaming.scache.Cache
import dev.profunktor.auth.jwt.JwtToken
import pdi.jwt.JwtClaim

import scala.concurrent.duration._
import scala.util.control.NoStackTrace

trait AuthService[F[_]] {
  def newUser(username: String, password: String): F[JwtToken]
  def login(username: String, password: String): F[JwtToken]
  def logout(token: JwtToken): F[Unit]
  def auth(token: JwtToken)(claim: JwtClaim): F[Option[UUID]]
}

object AuthService {
  def of[F[_]: Concurrent: Timer: Parallel](users: UserService[F], security: Security[F]): Resource[F, AuthService[F]] =
    Cache.expiring[F, JwtToken, UUID](1.hour).map { tokens =>
      new AuthService[F] {
        def newUser(username: String, password: String): F[JwtToken] =
          for {
            userId <- users.create(username, password)
            token  <- security.createJwtToken(userId)
            _      <- tokens.put(token, userId)
          } yield token
        def login(username: String, password: String): F[JwtToken] =
          users.find(username, password).flatMap {
            _.fold(InvalidUsernameOrPassword.raiseError[F, JwtToken]) { user =>
              security.createJwtToken(user.id).flatTap(tokens.put(_, user.id).void)
            }
          }
        def logout(token: JwtToken): F[Unit] = tokens.remove(token).void
        def auth(token: JwtToken)(claim: JwtClaim): F[Option[UUID]] =
          tokens.get(token).map(_.flatMap(id => security.parseUUID(claim.content).filter(_ == id)))
      }
    }

  final case object InvalidUsernameOrPassword extends NoStackTrace
}
