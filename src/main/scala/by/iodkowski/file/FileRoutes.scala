package by.iodkowski.file

import java.util.UUID

import by.iodkowski.http.ApplicationRoutes.V1
import cats.Parallel
import cats.effect.Sync
import cats.implicits._
import org.http4s.AuthedRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.multipart.Multipart

object FileRoutes {
  def of[F[_]: Parallel: Sync](fileService: FileService[F]): AuthedRoutes[UUID, F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    AuthedRoutes.of[UUID, F] {
      case req @ POST -> Root / V1 / "file" as userId =>
        req.req.decode[Multipart[F]](_.parts.traverse(fileService.upload(userId, _)) *> Ok())
    }
  }
}
