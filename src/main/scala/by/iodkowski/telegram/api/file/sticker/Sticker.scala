package by.iodkowski.telegram.api.file.sticker

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.MaskPosition
import by.iodkowski.telegram.api.file.{File, PhotoSize}

/**
  * Describes a sticker.
  *
  * @param setId        The identifier of the sticker set to which the sticker belongs; 0 if none.
  * @param width        Sticker width; as defined by the sender.
  * @param height       Sticker height; as defined by the sender.
  * @param emoji        Emoji corresponding to the sticker.
  * @param isAnimated   True, if the sticker is an animated sticker in TGS format.
  * @param isMask       True, if the sticker is a mask.
  * @param maskPosition Position where the mask should be placed; may be None.
  * @param thumbnail    Sticker thumbnail in WEBP or JPEG format; may be None.
  * @param sticker      File containing the sticker.
  */
final case class Sticker(
  setId: Long,
  width: Int,
  height: Int,
  emoji: String,
  isAnimated: Boolean,
  isMask: Boolean,
  maskPosition: Option[MaskPosition],
  thumbnail: Option[PhotoSize],
  sticker: File
)

private[api] object Sticker {
  def fromJava(o: TdApi.Sticker): Sticker =
    Sticker(
      o.setId,
      o.width,
      o.height,
      o.emoji,
      o.isAnimated,
      o.isMask,
      Option(o.maskPosition).map(MaskPosition.fromJava),
      Option(o.thumbnail).map(PhotoSize.fromJava),
      File.fromJava(o.sticker)
    )
}
