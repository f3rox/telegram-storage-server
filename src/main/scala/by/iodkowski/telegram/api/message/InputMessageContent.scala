package by.iodkowski.telegram.api.message

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api._
import by.iodkowski.telegram.api.file.{InputFile, InputThumbnail}
import by.iodkowski.telegram.api.poll.PollType
import by.iodkowski.telegram.api.text.FormattedText

/**
  * The content of a message to send.
  */
sealed abstract class InputMessageContent extends Product with Serializable

/**
  * A text message.
  *
  * @param text                  Formatted text to be sent; 1-GetOption(&quot;message_text_length_max&quot;) characters. Only Bold, Italic, Underline, Strikethrough, Code, Pre, PreCode, TextUrl and MentionName entities are allowed to be specified manually.
  * @param disableWebPagePreview True, if rich web page previews for URLs in the message text should be disabled.
  * @param clearDraft            True, if a chat message draft should be deleted.
  */
final case class InputMessageText(text: FormattedText, disableWebPagePreview: Boolean, clearDraft: Boolean)
    extends InputMessageContent

private[api] object InputMessageText {
  def fromJava(o: TdApi.InputMessageText): InputMessageText =
    InputMessageText(FormattedText.fromJava(o.text), o.disableWebPagePreview, o.clearDraft)
}

/**
  * An animation message (GIF-style).
  *
  * @param animation Animation file to be sent.
  * @param thumbnail Animation thumbnail, if available.
  * @param duration  Duration of the animation, in seconds.
  * @param width     Width of the animation; may be replaced by the server.
  * @param height    Height of the animation; may be replaced by the server.
  * @param caption   Animation caption; 0-GetOption(&quot;message_caption_length_max&quot;) characters.
  */
final case class InputMessageAnimation(
  animation: InputFile,
  thumbnail: Option[InputThumbnail],
  duration: Int,
  width: Int,
  height: Int,
  caption: FormattedText
) extends InputMessageContent

private[api] object InputMessageAnimation {
  def fromJava(o: TdApi.InputMessageAnimation): InputMessageAnimation =
    InputMessageAnimation(
      InputFile.fromJava(o.animation),
      Option(o.thumbnail).map(InputThumbnail.fromJava),
      o.duration,
      o.width,
      o.height,
      FormattedText.fromJava(o.caption)
    )
}

/**
  * An audio message.
  *
  * @param audio               Audio file to be sent.
  * @param albumCoverThumbnail Thumbnail of the cover for the album, if available.
  * @param duration            Duration of the audio, in seconds; may be replaced by the server.
  * @param title               Title of the audio; 0-64 characters; may be replaced by the server.
  * @param performer           Performer of the audio; 0-64 characters, may be replaced by the server.
  * @param caption             Audio caption; 0-GetOption(&quot;message_caption_length_max&quot;) characters.
  */
final case class InputMessageAudio(
  audio: InputFile,
  albumCoverThumbnail: Option[InputThumbnail],
  duration: Int,
  title: String,
  performer: String,
  caption: FormattedText
) extends InputMessageContent

private[api] object InputMessageAudio {
  def fromJava(o: TdApi.InputMessageAudio): InputMessageAudio =
    InputMessageAudio(
      InputFile.fromJava(o.audio),
      Option(o.albumCoverThumbnail).map(InputThumbnail.fromJava),
      o.duration,
      o.title,
      o.performer,
      FormattedText.fromJava(o.caption)
    )
}

/**
  * A document message (general file).
  *
  * @param document  Document to be sent.
  * @param thumbnail Document thumbnail, if available.
  * @param caption   Document caption; 0-GetOption(&quot;message_caption_length_max&quot;) characters.
  */
final case class InputMessageDocument(
  document: InputFile,
  thumbnail: Option[InputThumbnail],
  caption: FormattedText
) extends InputMessageContent

private[api] object InputMessageDocument {
  def fromJava(o: TdApi.InputMessageDocument): InputMessageDocument =
    InputMessageDocument(
      InputFile.fromJava(o.document),
      Option(o.thumbnail).map(InputThumbnail.fromJava),
      FormattedText.fromJava(o.caption)
    )
}

/**
  * A photo message.
  *
  * @param photo               Photo to send.
  * @param thumbnail           Photo thumbnail to be sent, this is sent to the other party in secret chats only.
  * @param addedStickerFileIds File identifiers of the stickers added to the photo, if applicable.
  * @param width               Photo width.
  * @param height              Photo height.
  * @param caption             Photo caption; 0-GetOption(&quot;message_caption_length_max&quot;) characters.
  * @param ttl                 Photo TTL (Time To Live), in seconds (0-60). A non-zero TTL can be specified only in private chats.
  */
final case class InputMessagePhoto(
  photo: InputFile,
  thumbnail: InputThumbnail,
  addedStickerFileIds: List[Int],
  width: Int,
  height: Int,
  caption: FormattedText,
  ttl: Int
) extends InputMessageContent

private[api] object InputMessagePhoto {
  def fromJava(o: TdApi.InputMessagePhoto): InputMessagePhoto =
    InputMessagePhoto(
      InputFile.fromJava(o.photo),
      InputThumbnail.fromJava(o.thumbnail),
      o.addedStickerFileIds.toList,
      o.width,
      o.height,
      FormattedText.fromJava(o.caption),
      o.ttl
    )
}

/**
  * A sticker message.
  *
  * @param sticker   Sticker to be sent.
  * @param thumbnail Sticker thumbnail, if available.
  * @param width     Sticker width.
  * @param height    Sticker height.
  */
final case class InputMessageSticker(
  sticker: InputFile,
  thumbnail: Option[InputThumbnail],
  width: Int,
  height: Int
) extends InputMessageContent

private[api] object InputMessageSticker {
  def fromJava(o: TdApi.InputMessageSticker): InputMessageSticker =
    InputMessageSticker(
      InputFile.fromJava(o.sticker),
      Option(o.thumbnail).map(InputThumbnail.fromJava),
      o.width,
      o.height
    )
}

/**
  * A video message.
  *
  * @param video               Video to be sent.
  * @param thumbnail           Video thumbnail, if available.
  * @param addedStickerFileIds File identifiers of the stickers added to the video, if applicable.
  * @param duration            Duration of the video, in seconds.
  * @param width               Video width.
  * @param height              Video height.
  * @param supportsStreaming   True, if the video should be tried to be streamed.
  * @param caption             Video caption; 0-GetOption(&quot;message_caption_length_max&quot;) characters.
  * @param ttl                 Video TTL (Time To Live), in seconds (0-60). A non-zero TTL can be specified only in private chats.
  */
final case class InputMessageVideo(
  video: InputFile,
  thumbnail: Option[InputThumbnail],
  addedStickerFileIds: List[Int],
  duration: Int,
  width: Int,
  height: Int,
  supportsStreaming: Boolean,
  caption: FormattedText,
  ttl: Int
) extends InputMessageContent

private[api] object InputMessageVideo {
  def fromJava(o: TdApi.InputMessageVideo): InputMessageVideo =
    InputMessageVideo(
      InputFile.fromJava(o.video),
      Option(o.thumbnail).map(InputThumbnail.fromJava),
      o.addedStickerFileIds.toList,
      o.duration,
      o.width,
      o.height,
      o.supportsStreaming,
      FormattedText.fromJava(o.caption),
      o.ttl
    )
}

/**
  * A video note message.
  *
  * @param videoNote Video note to be sent.
  * @param thumbnail Video thumbnail, if available.
  * @param duration  Duration of the video, in seconds.
  * @param length    Video width and height; must be positive and not greater than 640.
  */
final case class InputMessageVideoNote(
  videoNote: InputFile,
  thumbnail: Option[InputThumbnail],
  duration: Int,
  length: Int
) extends InputMessageContent

private[api] object InputMessageVideoNote {
  def fromJava(o: TdApi.InputMessageVideoNote): InputMessageVideoNote =
    InputMessageVideoNote(
      InputFile.fromJava(o.videoNote),
      Option(o.thumbnail).map(InputThumbnail.fromJava),
      o.duration,
      o.length
    )
}

/**
  * A voice note message.
  *
  * @param voiceNote Voice note to be sent.
  * @param duration  Duration of the voice note, in seconds.
  * @param waveform  Waveform representation of the voice note, in 5-bit format.
  * @param caption   Voice note caption; 0-GetOption(&quot;message_caption_length_max&quot;) characters.
  */
final case class InputMessageVoiceNote(
  voiceNote: InputFile,
  duration: Int,
  waveform: List[Byte],
  caption: FormattedText
) extends InputMessageContent

private[api] object InputMessageVoiceNote {
  def fromJava(o: TdApi.InputMessageVoiceNote): InputMessageVoiceNote =
    InputMessageVoiceNote(
      InputFile.fromJava(o.voiceNote),
      o.duration,
      o.waveform.toList,
      FormattedText.fromJava(o.caption)
    )
}

/**
  * A message with a location.
  *
  * @param location   Location to be sent.
  * @param livePeriod Period for which the location can be updated, in seconds; should be between 60 and 86400 for a live location and 0 otherwise.
  */
final case class InputMessageLocation(location: Location, livePeriod: Int) extends InputMessageContent

private[api] object InputMessageLocation {
  def fromJava(o: TdApi.InputMessageLocation): InputMessageLocation =
    InputMessageLocation(Location.fromJava(o.location), o.livePeriod)
}

/**
  * A message with information about a venue.
  *
  * @param venue Venue to send.
  */
final case class InputMessageVenue(venue: Venue) extends InputMessageContent

private[api] object InputMessageVenue {
  def fromJava(o: TdApi.InputMessageVenue): InputMessageVenue = InputMessageVenue(Venue.fromJava(o.venue))
}

/**
  * A message containing a user contact.
  *
  * @param contact Contact to send.
  */
final case class InputMessageContact(contact: Contact) extends InputMessageContent

private[api] object InputMessageContact {
  def fromJava(o: TdApi.InputMessageContact): InputMessageContact = InputMessageContact(Contact.fromJava(o.contact))
}

/**
  * A message with a game; not supported for channels or secret chats.
  *
  * @param botUserId     User identifier of the bot that owns the game.
  * @param gameShortName Short name of the game.
  */
final case class InputMessageGame(botUserId: Int, gameShortName: String) extends InputMessageContent

private[api] object InputMessageGame {
  def fromJava(o: TdApi.InputMessageGame): InputMessageGame = InputMessageGame(o.botUserId, o.gameShortName)
}

/**
  * A message with an invoice; can be used only by bots and only in private chats.
  *
  * @param invoice        Invoice.
  * @param title          Product title; 1-32 characters.
  * @param description    Product description; 0-255 characters.
  * @param photoUrl       Product photo URL; optional.
  * @param photoSize      Product photo size.
  * @param photoWidth     Product photo width.
  * @param photoHeight    Product photo height.
  * @param payload        The invoice payload.
  * @param providerToken  Payment provider token.
  * @param providerData   JSON-encoded data about the invoice, which will be shared with the payment provider.
  * @param startParameter Unique invoice bot startParameter for the generation of this invoice.
  */
final case class InputMessageInvoice(
  invoice: Invoice,
  title: String,
  description: String,
  photoUrl: String,
  photoSize: Int,
  photoWidth: Int,
  photoHeight: Int,
  payload: List[Byte],
  providerToken: String,
  providerData: String,
  startParameter: String
) extends InputMessageContent

private[api] object InputMessageInvoice {
  def fromJava(o: TdApi.InputMessageInvoice): InputMessageInvoice =
    InputMessageInvoice(
      Invoice.fromJava(o.invoice),
      o.title,
      o.description,
      o.photoUrl,
      o.photoSize,
      o.photoWidth,
      o.photoHeight,
      o.payload.toList,
      o.providerToken,
      o.providerData,
      o.startParameter
    )
}

/**
  * A message with a poll. Polls can't be sent to secret chats. Polls can be sent only to a private chat with a bot.
  *
  * @param question    Poll question, 1-255 characters.
  * @param options     List of poll answer options, 2-10 strings 1-100 characters each.
  * @param isAnonymous True, if the poll voters are anonymous. Non-anonymous polls can't be sent or forwarded to channels.
  * @param type        Type of the poll.
  * @param isClosed    True, if the poll needs to be sent already closed; for bots only.
  */
final case class InputMessagePoll(
  question: String,
  options: List[String],
  isAnonymous: Boolean,
  `type`: PollType,
  isClosed: Boolean
) extends InputMessageContent

private[api] object InputMessagePoll {
  def fromJava(o: TdApi.InputMessagePoll): InputMessagePoll =
    InputMessagePoll(o.question, o.options.toList, o.isAnonymous, PollType.fromJava(o.`type`), o.isClosed)
}

/**
  * A forwarded message.
  *
  * @param fromChatId    Identifier for the chat this forwarded message came from.
  * @param messageId     Identifier of the message to forward.
  * @param inGameShare   True, if a game message should be shared within a launched game; applies only to game messages.
  * @param sendCopy      True, if content of the message needs to be copied without a link to the original message. Always true if the message is forwarded to a secret chat.
  * @param removeCaption True, if media caption of the message copy needs to be removed. Ignored if sendCopy is false.
  */
final case class InputMessageForwarded(
  fromChatId: Long,
  messageId: Long,
  inGameShare: Boolean,
  sendCopy: Boolean,
  removeCaption: Boolean
) extends InputMessageContent

private[api] object InputMessageForwarded {
  def fromJava(o: TdApi.InputMessageForwarded): InputMessageForwarded =
    InputMessageForwarded(o.fromChatId, o.messageId, o.inGameShare, o.sendCopy, o.removeCaption)
}

private[api] object InputMessageContent {
  def fromJava(o: TdApi.InputMessageContent): InputMessageContent = o.getConstructor match {
    case TdApi.InputMessageText.CONSTRUCTOR  => InputMessageText.fromJava(o.asInstanceOf[TdApi.InputMessageText])
    case TdApi.InputMessageAudio.CONSTRUCTOR => InputMessageAudio.fromJava(o.asInstanceOf[TdApi.InputMessageAudio])
    case TdApi.InputMessagePhoto.CONSTRUCTOR => InputMessagePhoto.fromJava(o.asInstanceOf[TdApi.InputMessagePhoto])
    case TdApi.InputMessageVideo.CONSTRUCTOR => InputMessageVideo.fromJava(o.asInstanceOf[TdApi.InputMessageVideo])
    case TdApi.InputMessageVenue.CONSTRUCTOR => InputMessageVenue.fromJava(o.asInstanceOf[TdApi.InputMessageVenue])
    case TdApi.InputMessageGame.CONSTRUCTOR  => InputMessageGame.fromJava(o.asInstanceOf[TdApi.InputMessageGame])
    case TdApi.InputMessagePoll.CONSTRUCTOR  => InputMessagePoll.fromJava(o.asInstanceOf[TdApi.InputMessagePoll])
    case TdApi.InputMessageAnimation.CONSTRUCTOR =>
      InputMessageAnimation.fromJava(o.asInstanceOf[TdApi.InputMessageAnimation])
    case TdApi.InputMessageDocument.CONSTRUCTOR =>
      InputMessageDocument.fromJava(o.asInstanceOf[TdApi.InputMessageDocument])
    case TdApi.InputMessageSticker.CONSTRUCTOR =>
      InputMessageSticker.fromJava(o.asInstanceOf[TdApi.InputMessageSticker])
    case TdApi.InputMessageVideoNote.CONSTRUCTOR =>
      InputMessageVideoNote.fromJava(o.asInstanceOf[TdApi.InputMessageVideoNote])
    case TdApi.InputMessageVoiceNote.CONSTRUCTOR =>
      InputMessageVoiceNote.fromJava(o.asInstanceOf[TdApi.InputMessageVoiceNote])
    case TdApi.InputMessageLocation.CONSTRUCTOR =>
      InputMessageLocation.fromJava(o.asInstanceOf[TdApi.InputMessageLocation])
    case TdApi.InputMessageContact.CONSTRUCTOR =>
      InputMessageContact.fromJava(o.asInstanceOf[TdApi.InputMessageContact])
    case TdApi.InputMessageInvoice.CONSTRUCTOR =>
      InputMessageInvoice.fromJava(o.asInstanceOf[TdApi.InputMessageInvoice])
    case TdApi.InputMessageForwarded.CONSTRUCTOR =>
      InputMessageForwarded.fromJava(o.asInstanceOf[TdApi.InputMessageForwarded])
  }
}
