package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.Minithumbnail

/**
  * Describes a video note. The video must be equal in width and height, cropped to a circle, and stored in MPEG4 format.
  *
  * @param duration      Duration of the video, in seconds; as defined by the sender.
  * @param length        Video width and height; as defined by the sender.
  * @param minithumbnail Video minithumbnail; may be None.
  * @param thumbnail     Video thumbnail; as defined by the sender; may be None.
  * @param video         File containing the video.
  */
final case class VideoNote(
  duration: Int,
  length: Int,
  minithumbnail: Option[Minithumbnail],
  thumbnail: Option[PhotoSize],
  video: File
)

private[api] object VideoNote {
  def fromJava(o: TdApi.VideoNote): VideoNote =
    VideoNote(
      o.duration,
      o.length,
      Option(o.minithumbnail).map(Minithumbnail.fromJava),
      Option(o.thumbnail).map(PhotoSize.fromJava),
      File.fromJava(o.video)
    )
}
