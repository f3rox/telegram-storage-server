package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi

/**
  * Photo description.
  *
  * @param type   Thumbnail type (see https://core.telegram.org/constructor/photoSize).
  * @param photo  Information about the photo file.
  * @param width  Photo width.
  * @param height Photo height.
  */
final case class PhotoSize(`type`: String, photo: File, width: Int, height: Int)

private[api] object PhotoSize {
  def fromJava(o: TdApi.PhotoSize): PhotoSize = PhotoSize(o.`type`, File.fromJava(o.photo), o.width, o.height)
}
