package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.Minithumbnail

/**
  * Describes a photo.
  *
  * @param hasStickers   True, if stickers were added to the photo.
  * @param minithumbnail Photo minithumbnail; may be None.
  * @param sizes         Available variants of the photo, in different sizes.
  */
final case class Photo(
  hasStickers: Boolean,
  minithumbnail: Option[Minithumbnail],
  sizes: List[PhotoSize]
)

private[api] object Photo {
  def fromJava(o: TdApi.Photo): Photo =
    Photo(o.hasStickers, Option(o.minithumbnail).map(Minithumbnail.fromJava), o.sizes.map(PhotoSize.fromJava).toList)
}
