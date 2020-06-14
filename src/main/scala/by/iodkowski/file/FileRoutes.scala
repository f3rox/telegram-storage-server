package by.iodkowski.file

import java.util.UUID

import by.iodkowski.http.ApplicationRoutes.V1
import cats.Parallel
import cats.effect.Sync
import cats.implicits._
import org.http4s.dsl.Http4sDsl
import org.http4s.multipart.Multipart
import org.http4s.server.{AuthMiddleware, Router}
import org.http4s.{AuthedRoutes, HttpRoutes}

object FileRoutes {
  def of[F[_]: Parallel: Sync](
    fileService: FileService[F],
    authMiddleware: AuthMiddleware[F, UUID]
  ): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    val files =
      authMiddleware {
        AuthedRoutes.of[UUID, F] {
          case req @ POST -> Root as userId =>
            req.req.decode[Multipart[F]](_.parts.traverse(fileService.upload(userId, _)) *> Ok())
        }
      }
    Router(s"$V1/files" -> files)
  }
}
