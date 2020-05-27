package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.file.File

/**
  * Describes the photo of a chat.
  *
  * @param small A small (160x160) chat photo. The file can be downloaded only before the photo is changed.
  * @param big   A big (640x640) chat photo. The file can be downloaded only before the photo is changed.
  */
final case class ChatPhoto(small: File, big: File)

private[api] object ChatPhoto {
  def fromJava(o: TdApi.ChatPhoto): ChatPhoto = ChatPhoto(File.fromJava(o.small), File.fromJava(o.big))
}
