package by.iodkowski.telegram.api.file.sticker

import org.drinkless.tdlib.TdApi

/**
  * Represents a list of sticker sets.
  *
  * @param totalCount Approximate total number of sticker sets found.
  * @param sets       List of sticker sets.
  */
final case class StickerSets(totalCount: Int, sets: List[StickerSetInfo])

private[api] object StickerSets {
  def fromJava(o: TdApi.StickerSets): StickerSets =
    StickerSets(o.totalCount, o.sets.map(StickerSetInfo.fromJava).toList)
}
