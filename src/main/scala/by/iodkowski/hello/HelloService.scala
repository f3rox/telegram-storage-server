package by.iodkowski.hello

import cats.effect.Sync

trait HelloService[F[_]] {
  def hello(): F[String]
}

object HelloService {
  def create[F[_]: Sync]: HelloService[F] = () => Sync[F].delay("Hello!")
}
