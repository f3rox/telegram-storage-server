package by.iodkowski.telegram

import by.iodkowski.app.AppConfig.TelegramConfig
import by.iodkowski.telegram.api.Client
import cats.Parallel
import cats.effect.{ConcurrentEffect, Resource, Timer}

trait TelegramService[F[_]] {
  def client(phoneNumber: Long): F[Client[F]]
}

object TelegramService {
  def of[F[_]: ConcurrentEffect: Timer: Parallel](tdLibConfig: TelegramConfig): Resource[F, TelegramService[F]] = ???

}
