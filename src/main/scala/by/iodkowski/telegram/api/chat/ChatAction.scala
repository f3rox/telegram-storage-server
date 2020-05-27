package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi

/**
  * Describes the different types of activity in a chat.
  */
sealed abstract class ChatAction extends Product with Serializable

/**
  * The user is typing a message.
  */
case object ChatActionTyping extends ChatAction

/**
  * The user is recording a video.
  */
case object ChatActionRecordingVideo extends ChatAction

/**
  * The user is uploading a video.
  *
  * @param progress Upload progress, as a percentage.
  */
final case class ChatActionUploadingVideo(progress: Int) extends ChatAction

private[api] object ChatActionUploadingVideo {
  def fromJava(o: TdApi.ChatActionUploadingVideo): ChatActionUploadingVideo = ChatActionUploadingVideo(o.progress)
}

/**
  * The user is recording a voice note.
  */
case object ChatActionRecordingVoiceNote extends ChatAction

/**
  * The user is uploading a voice note.
  *
  * @param progress Upload progress, as a percentage.
  */
final case class ChatActionUploadingVoiceNote(progress: Int) extends ChatAction

private[api] object ChatActionUploadingVoiceNote {
  def fromJava(o: TdApi.ChatActionUploadingVoiceNote): ChatActionUploadingVoiceNote =
    ChatActionUploadingVoiceNote(o.progress)
}

/**
  * The user is uploading a photo.
  *
  * @param progress Upload progress, as a percentage.
  */
final case class ChatActionUploadingPhoto(progress: Int) extends ChatAction

private[api] object ChatActionUploadingPhoto {
  def fromJava(o: TdApi.ChatActionUploadingPhoto): ChatActionUploadingPhoto = ChatActionUploadingPhoto(o.progress)
}

/**
  * The user is uploading a document.
  *
  * @param progress Upload progress, as a percentage.
  */
final case class ChatActionUploadingDocument(progress: Int) extends ChatAction

private[api] object ChatActionUploadingDocument {
  def fromJava(o: TdApi.ChatActionUploadingDocument): ChatActionUploadingDocument =
    ChatActionUploadingDocument(o.progress)
}

/**
  * The user is picking a location or venue to send.
  */
case object ChatActionChoosingLocation extends ChatAction

/**
  * The user is picking a contact to send.
  */
case object ChatActionChoosingContact extends ChatAction

/**
  * The user has started to play a game.
  */
case object ChatActionStartPlayingGame extends ChatAction

/**
  * The user is recording a video note.
  */
case object ChatActionRecordingVideoNote extends ChatAction

/**
  * The user is uploading a video note.
  *
  * @param progress Upload progress, as a percentage.
  */
final case class ChatActionUploadingVideoNote(progress: Int) extends ChatAction

private[api] object ChatActionUploadingVideoNote {
  def fromJava(o: TdApi.ChatActionUploadingVideoNote): ChatActionUploadingVideoNote =
    ChatActionUploadingVideoNote(o.progress)
}

/**
  * The user has cancelled the previous action.
  */
case object ChatActionCancel extends ChatAction

private[api] object ChatAction {
  def fromJava(o: TdApi.ChatAction): ChatAction = o.getConstructor match {
    case TdApi.ChatActionTyping.CONSTRUCTOR             => ChatActionTyping
    case TdApi.ChatActionRecordingVideo.CONSTRUCTOR     => ChatActionRecordingVideo
    case TdApi.ChatActionChoosingLocation.CONSTRUCTOR   => ChatActionChoosingLocation
    case TdApi.ChatActionChoosingContact.CONSTRUCTOR    => ChatActionChoosingContact
    case TdApi.ChatActionStartPlayingGame.CONSTRUCTOR   => ChatActionStartPlayingGame
    case TdApi.ChatActionRecordingVideoNote.CONSTRUCTOR => ChatActionRecordingVideoNote
    case TdApi.ChatActionRecordingVoiceNote.CONSTRUCTOR => ChatActionRecordingVoiceNote
    case TdApi.ChatActionUploadingVideo.CONSTRUCTOR =>
      ChatActionUploadingVideo.fromJava(o.asInstanceOf[TdApi.ChatActionUploadingVideo])
    case TdApi.ChatActionUploadingVoiceNote.CONSTRUCTOR =>
      ChatActionUploadingVoiceNote.fromJava(o.asInstanceOf[TdApi.ChatActionUploadingVoiceNote])
    case TdApi.ChatActionUploadingPhoto.CONSTRUCTOR =>
      ChatActionUploadingPhoto.fromJava(o.asInstanceOf[TdApi.ChatActionUploadingPhoto])
    case TdApi.ChatActionUploadingDocument.CONSTRUCTOR =>
      ChatActionUploadingDocument.fromJava(o.asInstanceOf[TdApi.ChatActionUploadingDocument])
    case TdApi.ChatActionUploadingVideoNote.CONSTRUCTOR =>
      ChatActionUploadingVideoNote.fromJava(o.asInstanceOf[TdApi.ChatActionUploadingVideoNote])
    case TdApi.ChatActionCancel.CONSTRUCTOR => ChatActionCancel
  }
}
