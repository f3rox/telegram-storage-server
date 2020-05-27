package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi

/**
  * A thumbnail to be sent along with a file; should be in JPEG or WEBP format for stickers, and less than 200 kB in size.
  *
  * @param thumbnail Thumbnail file to send. Sending thumbnails by fileId is currently not supported.
  * @param width     Thumbnail width, usually shouldn't exceed 320. Use 0 if unknown.
  * @param height    Thumbnail height, usually shouldn't exceed 320. Use 0 if unknown.
  */
final case class InputThumbnail(thumbnail: InputFile, width: Int, height: Int)

private[api] object InputThumbnail {
  def fromJava(o: TdApi.InputThumbnail): InputThumbnail =
    InputThumbnail(InputFile.fromJava(o.thumbnail), o.width, o.height)
}
