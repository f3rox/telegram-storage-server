package by.iodkowski.telegram

import by.iodkowski.config.AppConfig.TdLibConfig
import cats.Parallel
import cats.effect.{ConcurrentEffect, Resource, Timer}
import com.evolutiongaming.scache.Cache

import scala.concurrent.duration._

trait TelegramService[F[_]] {
  def client(phoneNumber: Long): F[TelegramClient[F]]
}

object TelegramService {
  def of[F[_]: ConcurrentEffect: Timer: Parallel](tdLibConfig: TdLibConfig): Resource[F, TelegramService[F]] =
    Cache.expiring[F, Long, TelegramClient[F]](1.day).map { cache =>
      new TelegramService[F] {
        override def client(phoneNumber: Long): F[TelegramClient[F]] =
          cache.getOrUpdate(phoneNumber)(TelegramClient.of(phoneNumber, tdLibConfig))
      }
    }
}
