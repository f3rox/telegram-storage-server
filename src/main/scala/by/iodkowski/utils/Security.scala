package by.iodkowski.utils

import java.util.UUID
import java.util.concurrent.TimeUnit

import by.iodkowski.app.AppConfig.SecurityConfig
import by.iodkowski.auth.JwtAuthMiddleware.JwtUser
import cats.effect.{Clock, Sync}
import cats.implicits._
import dev.profunktor.auth.jwt._
import io.circe.syntax._
import pdi.jwt.{JwtAlgorithm, JwtClaim}

import scala.util.Try

trait Security[F[_]] {
  def randomUUID: F[UUID]
  def parseUUID(input: String): Option[UUID]
  def createJwtToken(userId: UUID): F[JwtToken]
  val userJwtAuth: JwtSymmetricAuth
}

object Security {
  def of[F[_]: Sync: Clock](config: SecurityConfig): Security[F] =
    new Security[F] {
      def randomUUID: F[UUID]                    = Sync[F].delay(java.util.UUID.randomUUID())
      def parseUUID(input: String): Option[UUID] = Try(UUID.fromString(input)).toOption
      def createJwtToken(userId: UUID): F[JwtToken] =
        for {
          issuedAt  <- Clock[F].realTime(TimeUnit.SECONDS)
          expiresAt = issuedAt + config.tokenExpiresIn.toSeconds
          jwtUser   = JwtUser(userId).asJson.noSpaces
          claim     = JwtClaim(jwtUser).issuedAt(issuedAt).expiresAt(expiresAt)
          secretKey = JwtSecretKey(config.jwtSecretKey)
          token     <- jwtEncode[F](claim, secretKey, JwtAlgorithm.HS256)
        } yield token
      val userJwtAuth: JwtSymmetricAuth = JwtAuth.hmac(config.jwtSecretKey, JwtAlgorithm.HS256)
    }
}
