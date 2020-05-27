package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.Minithumbnail

/**
  * Describes an animation file. The animation must be encoded in GIF or MPEG4 format.
  *
  * @param duration      Duration of the animation, in seconds; as defined by the sender.
  * @param width         Width of the animation.
  * @param height        Height of the animation.
  * @param fileName      Original name of the file; as defined by the sender.
  * @param mimeType      MIME type of the file, usually &quot;image/gif&quot; or &quot;video/mp4&quot;.
  * @param minithumbnail Animation minithumbnail; may be None.
  * @param thumbnail     Animation thumbnail; may be None.
  * @param animation     File containing the animation.
  */
final case class Animation(
  duration: Int,
  width: Int,
  height: Int,
  fileName: String,
  mimeType: String,
  minithumbnail: Option[Minithumbnail],
  thumbnail: Option[PhotoSize],
  animation: File
)

private[api] object Animation {
  def fromJava(o: TdApi.Animation): Animation =
    Animation(
      o.duration,
      o.width,
      o.height,
      o.fileName,
      o.mimeType,
      Option(o.minithumbnail).map(Minithumbnail.fromJava),
      Option(o.thumbnail).map(PhotoSize.fromJava),
      File.fromJava(o.animation)
    )
}
