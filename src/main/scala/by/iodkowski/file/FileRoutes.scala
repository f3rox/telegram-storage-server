package by.iodkowski.file

import java.util.UUID

import by.iodkowski.http.ApplicationRoutes.V1
import cats.Parallel
import cats.effect.Sync
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.multipart.Multipart

object FileRoutes {
  def of[F[_]: Parallel: Sync](fileService: FileService[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of {
      case req @ POST -> Root / V1 / "file" =>
        req.decode[Multipart[F]] { multipart =>
          val id = UUID.randomUUID() // TODO: Replace by user ID
          multipart.parts.traverse(fileService.upload(id, _)) *> Ok("Uploaded!")
        }
    }
  }
}
