package by.iodkowski.telegram

import cats.effect.Sync
import org.drinkless.tdlib.Client
import org.drinkless.tdlib.TdApi.SetLogVerbosityLevel

object TdLib {

  def loadLibrary[F[_]: Sync]: F[Unit] = Sync[F].delay(System.loadLibrary("tdjni"))

  def setLogVerbosityLevel[F[_]: Sync](level: Int): F[Unit] =
    Sync[F].delay(Client.execute(new SetLogVerbosityLevel(level)))
}
