package by.iodkowski

import cats.effect.{ExitCode, IO, IOApp, Sync}
import cats.implicits._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = runF[IO]

  private def runF[F[_]: Sync]: F[ExitCode] =
    Sync[F].delay(println("Hello!")) as ExitCode.Success
}
