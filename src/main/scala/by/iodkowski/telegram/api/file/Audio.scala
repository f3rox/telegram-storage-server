package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.Minithumbnail

/**
  * Describes an audio file. Audio is usually in MP3 or M4A format.
  *
  * @param duration                Duration of the audio, in seconds; as defined by the sender.
  * @param title                   Title of the audio; as defined by the sender.
  * @param performer               Performer of the audio; as defined by the sender.
  * @param fileName                Original name of the file; as defined by the sender.
  * @param mimeType                The MIME type of the file; as defined by the sender.
  * @param albumCoverMinithumbnail The minithumbnail of the album cover; may be None.
  * @param albumCoverThumbnail     The thumbnail of the album cover; as defined by the sender. The full size thumbnail should be extracted from the downloaded file; may be None.
  * @param audio                   File containing the audio.
  */
final case class Audio(
  duration: Int,
  title: String,
  performer: String,
  fileName: String,
  mimeType: String,
  albumCoverMinithumbnail: Option[Minithumbnail],
  albumCoverThumbnail: Option[PhotoSize],
  audio: File
)

private[api] object Audio {
  def fromJava(o: TdApi.Audio): Audio =
    Audio(
      o.duration,
      o.title,
      o.performer,
      o.fileName,
      o.mimeType,
      Option(o.albumCoverMinithumbnail).map(Minithumbnail.fromJava),
      Option(o.albumCoverThumbnail).map(PhotoSize.fromJava),
      File.fromJava(o.audio)
    )
}
