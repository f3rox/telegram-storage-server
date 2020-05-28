package by.iodkowski.auth

import cats.effect.{Concurrent, Sync}
import fs2.concurrent.Queue
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.websocket.WebSocketBuilder
import org.http4s.websocket.WebSocketFrame
import cats.implicits._

object AuthSocket {
  def of[F[_]: Concurrent]: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / LongVar(phoneNumber) =>
        println(phoneNumber)
        Queue.unbounded[F, WebSocketFrame].flatMap { q => WebSocketBuilder[F].build(q.dequeue, q.enqueue) }
    }
  }
}
