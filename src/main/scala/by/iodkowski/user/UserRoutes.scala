package by.iodkowski.user

import by.iodkowski.http.ApplicationRoutes.V1
import by.iodkowski.user.UserService.UserNameInUse
import cats.effect.Sync
import cats.implicits._
import io.circe.derivation.annotations.{Configuration, JsonCodec}
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.dsl.Http4sDsl

object UserRoutes {
  def of[F[_]: Sync](users: UserService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of {
      case req @ POST -> Root / V1 / "users" =>
        req.decode[CreateUser] { user =>
          users
            .create(user.username, user.password)
            .flatMap(_ => Created())
            .recoverWith {
              case UserNameInUse(username) => Conflict(s"$username")
            }
        }
    }
  }

  @JsonCodec(Configuration.decodeOnly)
  final case class CreateUser(username: String, password: String)
}
