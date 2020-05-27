package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.Minithumbnail

/**
  * Describes a video file.
  *
  * @param duration          Duration of the video, in seconds; as defined by the sender.
  * @param width             Video width; as defined by the sender.
  * @param height            Video height; as defined by the sender.
  * @param fileName          Original name of the file; as defined by the sender.
  * @param mimeType          MIME type of the file; as defined by the sender.
  * @param hasStickers       True, if stickers were added to the video.
  * @param supportsStreaming True, if the video should be tried to be streamed.
  * @param minithumbnail     Video minithumbnail; may be None.
  * @param thumbnail         Video thumbnail; as defined by the sender; may be None.
  * @param video             File containing the video.
  */
final case class Video(
  duration: Int,
  width: Int,
  height: Int,
  fileName: String,
  mimeType: String,
  hasStickers: Boolean,
  supportsStreaming: Boolean,
  minithumbnail: Option[Minithumbnail],
  thumbnail: Option[PhotoSize],
  video: File
)

private[api] object Video {
  def fromJava(o: TdApi.Video): Video =
    Video(
      o.duration,
      o.width,
      o.height,
      o.fileName,
      o.mimeType,
      o.hasStickers,
      o.supportsStreaming,
      Option(o.minithumbnail).map(Minithumbnail.fromJava),
      Option(o.thumbnail).map(PhotoSize.fromJava),
      File.fromJava(o.video)
    )
}
