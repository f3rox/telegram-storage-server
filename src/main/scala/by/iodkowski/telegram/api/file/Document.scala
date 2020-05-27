package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.Minithumbnail

/**
  * Describes a document of any type.
  *
  * @param fileName      Original name of the file; as defined by the sender.
  * @param mimeType      MIME type of the file; as defined by the sender.
  * @param minithumbnail Document minithumbnail; may be None.
  * @param thumbnail     Document thumbnail in JPEG or PNG format (PNG will be used only for background patterns); as defined by the sender; may be None.
  * @param document      File containing the document.
  */
final case class Document(
  fileName: String,
  mimeType: String,
  minithumbnail: Option[Minithumbnail],
  thumbnail: Option[PhotoSize],
  document: File
)

private[api] object Document {
  def fromJava(o: TdApi.Document): Document =
    Document(
      o.fileName,
      o.mimeType,
      Option(o.minithumbnail).map(Minithumbnail.fromJava),
      Option(o.thumbnail).map(PhotoSize.fromJava),
      File.fromJava(o.document)
    )
}
