package by.iodkowski.telegram.api.notification

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.file._
import by.iodkowski.telegram.api.file.sticker.Sticker

/**
  * Contains content of a push message notification.
  */
sealed abstract class PushMessageContent extends Product with Serializable

/**
  * A general message with hidden content.
  *
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentHidden(isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentHidden {
  def fromJava(o: TdApi.PushMessageContentHidden): PushMessageContentHidden = PushMessageContentHidden(o.isPinned)
}

/**
  * An animation message (GIF-style).
  *
  * @param animation Message content; may be None.
  * @param caption   Animation caption.
  * @param isPinned  True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentAnimation(animation: Option[Animation], caption: String, isPinned: Boolean)
    extends PushMessageContent

private[api] object PushMessageContentAnimation {
  def fromJava(o: TdApi.PushMessageContentAnimation): PushMessageContentAnimation =
    PushMessageContentAnimation(Option(o.animation).map(Animation.fromJava), o.caption, o.isPinned)
}

/**
  * An audio message.
  *
  * @param audio    Message content; may be None.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentAudio(audio: Option[Audio], isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentAudio {
  def fromJava(o: TdApi.PushMessageContentAudio): PushMessageContentAudio =
    PushMessageContentAudio(Option(o.audio).map(Audio.fromJava), o.isPinned)
}

/**
  * A message with a user contact.
  *
  * @param name     Contact's name.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentContact(name: String, isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentContact {
  def fromJava(o: TdApi.PushMessageContentContact): PushMessageContentContact =
    PushMessageContentContact(o.name, o.isPinned)
}

/**
  * A contact has registered with Telegram.
  */
case object PushMessageContentContactRegistered extends PushMessageContent

/**
  * A document message (a general file).
  *
  * @param document Message content; may be None.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentDocument(document: Option[Document], isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentDocument {
  def fromJava(o: TdApi.PushMessageContentDocument): PushMessageContentDocument =
    PushMessageContentDocument(Option(o.document).map(Document.fromJava), o.isPinned)
}

/**
  * A message with a game.
  *
  * @param title    Game title, empty for pinned game message.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentGame(title: String, isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentGame {
  def fromJava(o: TdApi.PushMessageContentGame): PushMessageContentGame = PushMessageContentGame(o.title, o.isPinned)
}

/**
  * A new high score was achieved in a game.
  *
  * @param title    Game title, empty for pinned message.
  * @param score    New score, 0 for pinned message.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentGameScore(title: String, score: Int, isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentGameScore {
  def fromJava(o: TdApi.PushMessageContentGameScore): PushMessageContentGameScore =
    PushMessageContentGameScore(o.title, o.score, o.isPinned)
}

/**
  * A message with an invoice from a bot.
  *
  * @param price    Product price.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentInvoice(price: String, isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentInvoice {
  def fromJava(o: TdApi.PushMessageContentInvoice): PushMessageContentInvoice =
    PushMessageContentInvoice(o.price, o.isPinned)
}

/**
  * A message with a location.
  *
  * @param isLive   True, if the location is live.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentLocation(isLive: Boolean, isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentLocation {
  def fromJava(o: TdApi.PushMessageContentLocation): PushMessageContentLocation =
    PushMessageContentLocation(o.isLive, o.isPinned)
}

/**
  * A photo message.
  *
  * @param photo    Message content; may be None.
  * @param caption  Photo caption.
  * @param isSecret True, if the photo is secret.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentPhoto(photo: Option[Photo], caption: String, isSecret: Boolean, isPinned: Boolean)
    extends PushMessageContent

private[api] object PushMessageContentPhoto {
  def fromJava(o: TdApi.PushMessageContentPhoto): PushMessageContentPhoto =
    PushMessageContentPhoto(Option(o.photo).map(Photo.fromJava), o.caption, o.isSecret, o.isPinned)
}

/**
  * A message with a poll.
  *
  * @param question  Poll question.
  * @param isRegular True, if the poll is regular and not in quiz mode.
  * @param isPinned  True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentPoll(question: String, isRegular: Boolean, isPinned: Boolean)
    extends PushMessageContent

private[api] object PushMessageContentPoll {
  def fromJava(o: TdApi.PushMessageContentPoll): PushMessageContentPoll =
    PushMessageContentPoll(o.question, o.isRegular, o.isPinned)
}

/**
  * A screenshot of a message in the chat has been taken.
  */
case object PushMessageContentScreenshotTaken extends PushMessageContent

/**
  * A message with a sticker.
  *
  * @param sticker  Message content; may be None.
  * @param emoji    Emoji corresponding to the sticker; may be empty.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentSticker(sticker: Option[Sticker], emoji: String, isPinned: Boolean)
    extends PushMessageContent

private[api] object PushMessageContentSticker {
  def fromJava(o: TdApi.PushMessageContentSticker): PushMessageContentSticker =
    PushMessageContentSticker(Option(o.sticker).map(Sticker.fromJava), o.emoji, o.isPinned)
}

/**
  * A text message.
  *
  * @param text     Message text.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentText(text: String, isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentText {
  def fromJava(o: TdApi.PushMessageContentText): PushMessageContentText = PushMessageContentText(o.text, o.isPinned)
}

/**
  * A video message.
  *
  * @param video    Message content; may be None.
  * @param caption  Video caption.
  * @param isSecret True, if the video is secret.
  * @param isPinned True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentVideo(video: Option[Video], caption: String, isSecret: Boolean, isPinned: Boolean)
    extends PushMessageContent

private[api] object PushMessageContentVideo {
  def fromJava(o: TdApi.PushMessageContentVideo): PushMessageContentVideo =
    PushMessageContentVideo(Option(o.video).map(Video.fromJava), o.caption, o.isSecret, o.isPinned)
}

/**
  * A video note message.
  *
  * @param videoNote Message content; may be None.
  * @param isPinned  True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentVideoNote(videoNote: Option[VideoNote], isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentVideoNote {
  def fromJava(o: TdApi.PushMessageContentVideoNote): PushMessageContentVideoNote =
    PushMessageContentVideoNote(Option(o.videoNote).map(VideoNote.fromJava), o.isPinned)
}

/**
  * A voice note message.
  *
  * @param voiceNote Message content; may be None.
  * @param isPinned  True, if the message is a pinned message with the specified content.
  */
final case class PushMessageContentVoiceNote(voiceNote: Option[VoiceNote], isPinned: Boolean) extends PushMessageContent

private[api] object PushMessageContentVoiceNote {
  def fromJava(o: TdApi.PushMessageContentVoiceNote): PushMessageContentVoiceNote =
    PushMessageContentVoiceNote(Option(o.voiceNote).map(VoiceNote.fromJava), o.isPinned)
}

/**
  * A newly created basic group.
  */
case object PushMessageContentBasicGroupChatCreate extends PushMessageContent

/**
  * New chat members were invited to a group.
  *
  * @param memberName    Name of the added member.
  * @param isCurrentUser True, if the current user was added to the group.
  * @param isReturned    True, if the user has returned to the group themself.
  */
final case class PushMessageContentChatAddMembers(memberName: String, isCurrentUser: Boolean, isReturned: Boolean)
    extends PushMessageContent

private[api] object PushMessageContentChatAddMembers {
  def fromJava(o: TdApi.PushMessageContentChatAddMembers): PushMessageContentChatAddMembers =
    PushMessageContentChatAddMembers(o.memberName, o.isCurrentUser, o.isReturned)
}

/**
  * A chat photo was edited.
  */
case object PushMessageContentChatChangePhoto extends PushMessageContent

/**
  * A chat title was edited.
  *
  * @param title New chat title.
  */
final case class PushMessageContentChatChangeTitle(title: String) extends PushMessageContent

private[api] object PushMessageContentChatChangeTitle {
  def fromJava(o: TdApi.PushMessageContentChatChangeTitle): PushMessageContentChatChangeTitle =
    PushMessageContentChatChangeTitle(o.title)
}

/**
  * A chat member was deleted.
  *
  * @param memberName    Name of the deleted member.
  * @param isCurrentUser True, if the current user was deleted from the group.
  * @param isLeft        True, if the user has left the group themself.
  */
final case class PushMessageContentChatDeleteMember(memberName: String, isCurrentUser: Boolean, isLeft: Boolean)
    extends PushMessageContent

private[api] object PushMessageContentChatDeleteMember {
  def fromJava(o: TdApi.PushMessageContentChatDeleteMember): PushMessageContentChatDeleteMember =
    PushMessageContentChatDeleteMember(o.memberName, o.isCurrentUser, o.isLeft)
}

/**
  * A new member joined the chat by invite link.
  */
case object PushMessageContentChatJoinByLink extends PushMessageContent

/**
  * A forwarded messages.
  *
  * @param totalCount Number of forwarded messages.
  */
final case class PushMessageContentMessageForwards(totalCount: Int) extends PushMessageContent

private[api] object PushMessageContentMessageForwards {
  def fromJava(o: TdApi.PushMessageContentMessageForwards): PushMessageContentMessageForwards =
    PushMessageContentMessageForwards(o.totalCount)
}

/**
  * A media album.
  *
  * @param totalCount Number of messages in the album.
  * @param hasPhotos  True, if the album has at least one photo.
  * @param hasVideos  True, if the album has at least one video.
  */
final case class PushMessageContentMediaAlbum(totalCount: Int, hasPhotos: Boolean, hasVideos: Boolean)
    extends PushMessageContent

private[api] object PushMessageContentMediaAlbum {
  def fromJava(o: TdApi.PushMessageContentMediaAlbum): PushMessageContentMediaAlbum =
    PushMessageContentMediaAlbum(o.totalCount, o.hasPhotos, o.hasVideos)
}

private[api] object PushMessageContent {
  def fromJava(o: TdApi.PushMessageContent): PushMessageContent = o.getConstructor match {
    case TdApi.PushMessageContentBasicGroupChatCreate.CONSTRUCTOR => PushMessageContentBasicGroupChatCreate
    case TdApi.PushMessageContentChatChangePhoto.CONSTRUCTOR      => PushMessageContentChatChangePhoto
    case TdApi.PushMessageContentChatJoinByLink.CONSTRUCTOR       => PushMessageContentChatJoinByLink
    case TdApi.PushMessageContentContactRegistered.CONSTRUCTOR    => PushMessageContentContactRegistered
    case TdApi.PushMessageContentScreenshotTaken.CONSTRUCTOR      => PushMessageContentScreenshotTaken
    case TdApi.PushMessageContentHidden.CONSTRUCTOR =>
      PushMessageContentHidden.fromJava(o.asInstanceOf[TdApi.PushMessageContentHidden])
    case TdApi.PushMessageContentAnimation.CONSTRUCTOR =>
      PushMessageContentAnimation.fromJava(o.asInstanceOf[TdApi.PushMessageContentAnimation])
    case TdApi.PushMessageContentAudio.CONSTRUCTOR =>
      PushMessageContentAudio.fromJava(o.asInstanceOf[TdApi.PushMessageContentAudio])
    case TdApi.PushMessageContentContact.CONSTRUCTOR =>
      PushMessageContentContact.fromJava(o.asInstanceOf[TdApi.PushMessageContentContact])
    case TdApi.PushMessageContentDocument.CONSTRUCTOR =>
      PushMessageContentDocument.fromJava(o.asInstanceOf[TdApi.PushMessageContentDocument])
    case TdApi.PushMessageContentGame.CONSTRUCTOR =>
      PushMessageContentGame.fromJava(o.asInstanceOf[TdApi.PushMessageContentGame])
    case TdApi.PushMessageContentGameScore.CONSTRUCTOR =>
      PushMessageContentGameScore.fromJava(o.asInstanceOf[TdApi.PushMessageContentGameScore])
    case TdApi.PushMessageContentInvoice.CONSTRUCTOR =>
      PushMessageContentInvoice.fromJava(o.asInstanceOf[TdApi.PushMessageContentInvoice])
    case TdApi.PushMessageContentLocation.CONSTRUCTOR =>
      PushMessageContentLocation.fromJava(o.asInstanceOf[TdApi.PushMessageContentLocation])
    case TdApi.PushMessageContentPhoto.CONSTRUCTOR =>
      PushMessageContentPhoto.fromJava(o.asInstanceOf[TdApi.PushMessageContentPhoto])
    case TdApi.PushMessageContentPoll.CONSTRUCTOR =>
      PushMessageContentPoll.fromJava(o.asInstanceOf[TdApi.PushMessageContentPoll])
    case TdApi.PushMessageContentSticker.CONSTRUCTOR =>
      PushMessageContentSticker.fromJava(o.asInstanceOf[TdApi.PushMessageContentSticker])
    case TdApi.PushMessageContentText.CONSTRUCTOR =>
      PushMessageContentText.fromJava(o.asInstanceOf[TdApi.PushMessageContentText])
    case TdApi.PushMessageContentVideo.CONSTRUCTOR =>
      PushMessageContentVideo.fromJava(o.asInstanceOf[TdApi.PushMessageContentVideo])
    case TdApi.PushMessageContentVideoNote.CONSTRUCTOR =>
      PushMessageContentVideoNote.fromJava(o.asInstanceOf[TdApi.PushMessageContentVideoNote])
    case TdApi.PushMessageContentVoiceNote.CONSTRUCTOR =>
      PushMessageContentVoiceNote.fromJava(o.asInstanceOf[TdApi.PushMessageContentVoiceNote])
    case TdApi.PushMessageContentChatAddMembers.CONSTRUCTOR =>
      PushMessageContentChatAddMembers.fromJava(o.asInstanceOf[TdApi.PushMessageContentChatAddMembers])
    case TdApi.PushMessageContentChatChangeTitle.CONSTRUCTOR =>
      PushMessageContentChatChangeTitle.fromJava(o.asInstanceOf[TdApi.PushMessageContentChatChangeTitle])
    case TdApi.PushMessageContentChatDeleteMember.CONSTRUCTOR =>
      PushMessageContentChatDeleteMember.fromJava(o.asInstanceOf[TdApi.PushMessageContentChatDeleteMember])
    case TdApi.PushMessageContentMessageForwards.CONSTRUCTOR =>
      PushMessageContentMessageForwards.fromJava(o.asInstanceOf[TdApi.PushMessageContentMessageForwards])
    case TdApi.PushMessageContentMediaAlbum.CONSTRUCTOR =>
      PushMessageContentMediaAlbum.fromJava(o.asInstanceOf[TdApi.PushMessageContentMediaAlbum])
  }
}
