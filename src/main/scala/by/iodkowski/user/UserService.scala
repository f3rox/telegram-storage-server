package by.iodkowski.user

import java.util.UUID

import by.iodkowski.user.UserQueries._
import by.iodkowski.utils.common._
import cats.effect.{Resource, Sync}
import cats.implicits._
import skunk.Session

trait UserService[F[_]] {
  def create(username: String, password: String): F[UUID]
  def find(username: String, password: String): F[Option[User]]
}

object UserService {
  def of[F[_]: Sync](sessionPool: Resource[F, Session[F]]): F[UserService[F]] =
    Sync[F].delay {
      new UserService[F] {
        def create(username: String, password: String): F[UUID] =
          sessionPool.use { session =>
            session.prepare(insertUser).use { cmd =>
              randomUUID.flatMap { uuid =>
                cmd
                  .execute(User(uuid, username, password))
                  .as(uuid)
              }
            }
          }
        def find(username: String, password: String): F[Option[User]] =
          sessionPool.use { session =>
            session.prepare(selectUser).use { query =>
              query
                .option(username)
                .map(_.filter(_.password == password))
            }
          }
      }
    }
}
