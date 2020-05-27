package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi

/**
  * Describes a voice note. The voice note must be encoded with the Opus codec, and stored inside an OGG container. Voice notes can have only a single audio channel.
  *
  * @param duration Duration of the voice note, in seconds; as defined by the sender.
  * @param waveform A waveform representation of the voice note in 5-bit format.
  * @param mimeType MIME type of the file; as defined by the sender.
  * @param voice    File containing the voice note.
  */
final case class VoiceNote(duration: Int, waveform: List[Byte], mimeType: String, voice: File)

private[api] object VoiceNote {
  def fromJava(o: TdApi.VoiceNote): VoiceNote =
    VoiceNote(o.duration, o.waveform.toList, o.mimeType, File.fromJava(o.voice))
}
