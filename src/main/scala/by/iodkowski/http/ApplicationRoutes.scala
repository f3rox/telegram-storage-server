package by.iodkowski.http

import by.iodkowski.file.{FileRoutes, FileService}
import by.iodkowski.hello.{HelloRoutes, HelloService}
import cats.Parallel
import cats.effect.Concurrent
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.server.Router

object ApplicationRoutes {

  val V1: String = "v1"

  def of[F[_]: Concurrent: Parallel](helloService: HelloService[F], fileService: FileService[F]): HttpRoutes[F] = {
    val apiRoutes = HelloRoutes.of(helloService) <+> FileRoutes.of(fileService)
    Router("/api" -> apiRoutes)
  }
}
