package by.iodkowski.telegram.api

import by.iodkowski.telegram.api.file.{Animation, Photo}
import by.iodkowski.telegram.api.text.FormattedText
import org.drinkless.tdlib.TdApi

/**
  * Describes a game.
  *
  * @param id          Game ID.
  * @param shortName   Game short name. To share a game use the URL https://t.me/{botUsername}?game={gameShortName}.
  * @param title       Game title.
  * @param text        Game text, usually containing scoreboards for a game.
  * @param description Game description.
  * @param photo       Game photo.
  * @param animation   Game animation; may be None.
  */
final case class Game(
  id: Long,
  shortName: String,
  title: String,
  text: FormattedText,
  description: String,
  photo: Photo,
  animation: Option[Animation]
)

private[api] object Game {
  def fromJava(o: TdApi.Game): Game =
    Game(
      o.id,
      o.shortName,
      o.title,
      FormattedText.fromJava(o.text),
      o.description,
      Photo.fromJava(o.photo),
      Option(o.animation).map(Animation.fromJava)
    )
}
