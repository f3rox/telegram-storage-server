package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Thumbnail image of a very poor quality and low resolution.
  *
  * @param width  Thumbnail width, usually doesn't exceed 40.
  * @param height Thumbnail height, usually doesn't exceed 40.
  * @param data   The thumbnail in JPEG format.
  */
final case class Minithumbnail(width: Int, height: Int, data: List[Byte])

private[api] object Minithumbnail {
  def fromJava(o: TdApi.Minithumbnail): Minithumbnail = Minithumbnail(o.width, o.height, o.data.toList)
}
