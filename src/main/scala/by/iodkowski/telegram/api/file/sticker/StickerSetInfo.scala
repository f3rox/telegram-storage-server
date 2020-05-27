package by.iodkowski.telegram.api.file.sticker

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.file.PhotoSize

/**
  * Represents short information about a sticker set.
  *
  * @param id          Identifier of the sticker set.
  * @param title       Title of the sticker set.
  * @param name        Name of the sticker set.
  * @param thumbnail   Sticker set thumbnail in WEBP format with width and height 100; may be None.
  * @param isInstalled True, if the sticker set has been installed by current user.
  * @param isArchived  True, if the sticker set has been archived. A sticker set can't be installed and archived simultaneously.
  * @param isOfficial  True, if the sticker set is official.
  * @param isAnimated  True, is the stickers in the set are animated.
  * @param isMasks     True, if the stickers in the set are masks.
  * @param isViewed    True for already viewed trending sticker sets.
  * @param size        Total number of stickers in the set.
  * @param covers      Contains up to the first 5 stickers from the set, depending on the context. If the client needs more stickers the full set should be requested.
  */
final case class StickerSetInfo(
  id: Long,
  title: String,
  name: String,
  thumbnail: Option[PhotoSize],
  isInstalled: Boolean,
  isArchived: Boolean,
  isOfficial: Boolean,
  isAnimated: Boolean,
  isMasks: Boolean,
  isViewed: Boolean,
  size: Int,
  covers: List[Sticker]
)

private[api] object StickerSetInfo {
  def fromJava(o: TdApi.StickerSetInfo): StickerSetInfo =
    StickerSetInfo(
      o.id,
      o.title,
      o.name,
      Option(o.thumbnail).map(PhotoSize.fromJava),
      o.isInstalled,
      o.isArchived,
      o.isOfficial,
      o.isAnimated,
      o.isMasks,
      o.isViewed,
      o.size,
      o.covers.map(Sticker.fromJava).toList
    )
}
