package by.iodkowski.telegram.api.file.background

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.file.Document

/**
  * Describes a chat background.
  *
  * @param id        Unique background identifier.
  * @param isDefault True, if this is one of default backgrounds.
  * @param isDark    True, if the background is dark and is recommended to be used with dark theme.
  * @param name      Unique background name.
  * @param document  Document with the background; may be None. None only for filled backgrounds.
  * @param type      Type of the background.
  */
final case class Background(
  id: Long,
  isDefault: Boolean,
  isDark: Boolean,
  name: String,
  document: Option[Document],
  `type`: BackgroundType
)

private[api] object Background {
  def fromJava(o: TdApi.Background): Background =
    Background(
      o.id,
      o.isDefault,
      o.isDark,
      o.name,
      Option(o.document).map(Document.fromJava),
      BackgroundType.fromJava(o.`type`)
    )
}
