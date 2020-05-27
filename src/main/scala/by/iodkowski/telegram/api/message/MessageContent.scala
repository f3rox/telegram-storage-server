package by.iodkowski.telegram.api.message

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api._
import by.iodkowski.telegram.api.call.CallDiscardReason
import by.iodkowski.telegram.api.file._
import by.iodkowski.telegram.api.file.sticker.Sticker
import by.iodkowski.telegram.api.passport._
import by.iodkowski.telegram.api.poll.Poll
import by.iodkowski.telegram.api.text.FormattedText

/**
  * Contains the content of a message.
  */
sealed abstract class MessageContent extends Product with Serializable

/**
  * A text message.
  *
  * @param text    Text of the message.
  * @param webPage A preview of the web page that's mentioned in the text; may be None.
  */
final case class MessageText(text: FormattedText, webPage: Option[WebPage]) extends MessageContent

private[api] object MessageText {
  def fromJava(o: TdApi.MessageText): MessageText =
    MessageText(FormattedText.fromJava(o.text), Option(o.webPage).map(WebPage.fromJava))
}

/**
  * An animation message (GIF-style).
  *
  * @param animation The animation description.
  * @param caption   Animation caption.
  * @param isSecret  True, if the animation thumbnail must be blurred and the animation must be shown only while tapped.
  */
final case class MessageAnimation(animation: Animation, caption: FormattedText, isSecret: Boolean)
    extends MessageContent

private[api] object MessageAnimation {
  def fromJava(o: TdApi.MessageAnimation): MessageAnimation =
    MessageAnimation(Animation.fromJava(o.animation), FormattedText.fromJava(o.caption), o.isSecret)
}

/**
  * An audio message.
  *
  * @param audio   The audio description.
  * @param caption Audio caption.
  */
final case class MessageAudio(audio: Audio, caption: FormattedText) extends MessageContent

private[api] object MessageAudio {
  def fromJava(o: TdApi.MessageAudio): MessageAudio =
    MessageAudio(Audio.fromJava(o.audio), FormattedText.fromJava(o.caption))
}

/**
  * A document message (general file).
  *
  * @param document The document description.
  * @param caption  Document caption.
  */
final case class MessageDocument(document: Document, caption: FormattedText) extends MessageContent

private[api] object MessageDocument {
  def fromJava(o: TdApi.MessageDocument): MessageDocument =
    MessageDocument(Document.fromJava(o.document), FormattedText.fromJava(o.caption))
}

/**
  * A photo message.
  *
  * @param photo    The photo description.
  * @param caption  Photo caption.
  * @param isSecret True, if the photo must be blurred and must be shown only while tapped.
  */
final case class MessagePhoto(photo: Photo, caption: FormattedText, isSecret: Boolean) extends MessageContent

private[api] object MessagePhoto {
  def fromJava(o: TdApi.MessagePhoto): MessagePhoto =
    MessagePhoto(Photo.fromJava(o.photo), FormattedText.fromJava(o.caption), o.isSecret)
}

/**
  * An expired photo message (self-destructed after TTL has elapsed).
  */
case object MessageExpiredPhoto extends MessageContent

/**
  * A sticker message.
  *
  * @param sticker The sticker description.
  */
final case class MessageSticker(sticker: Sticker) extends MessageContent

private[api] object MessageSticker {
  def fromJava(o: TdApi.MessageSticker): MessageSticker = MessageSticker(Sticker.fromJava(o.sticker))
}

/**
  * A video message.
  *
  * @param video    The video description.
  * @param caption  Video caption.
  * @param isSecret True, if the video thumbnail must be blurred and the video must be shown only while tapped.
  */
final case class MessageVideo(video: Video, caption: FormattedText, isSecret: Boolean) extends MessageContent

private[api] object MessageVideo {
  def fromJava(o: TdApi.MessageVideo): MessageVideo =
    MessageVideo(Video.fromJava(o.video), FormattedText.fromJava(o.caption), o.isSecret)
}

/**
  * An expired video message (self-destructed after TTL has elapsed).
  */
case object MessageExpiredVideo extends MessageContent

/**
  * A video note message.
  *
  * @param videoNote The video note description.
  * @param isViewed  True, if at least one of the recipients has viewed the video note.
  * @param isSecret  True, if the video note thumbnail must be blurred and the video note must be shown only while tapped.
  */
final case class MessageVideoNote(videoNote: VideoNote, isViewed: Boolean, isSecret: Boolean) extends MessageContent

private[api] object MessageVideoNote {
  def fromJava(o: TdApi.MessageVideoNote): MessageVideoNote =
    MessageVideoNote(VideoNote.fromJava(o.videoNote), o.isViewed, o.isSecret)
}

/**
  * A voice note message.
  *
  * @param voiceNote  The voice note description.
  * @param caption    Voice note caption.
  * @param isListened True, if at least one of the recipients has listened to the voice note.
  */
final case class MessageVoiceNote(voiceNote: VoiceNote, caption: FormattedText, isListened: Boolean)
    extends MessageContent

private[api] object MessageVoiceNote {
  def fromJava(o: TdApi.MessageVoiceNote): MessageVoiceNote =
    MessageVoiceNote(VoiceNote.fromJava(o.voiceNote), FormattedText.fromJava(o.caption), o.isListened)
}

/**
  * A message with a location.
  *
  * @param location   The location description.
  * @param livePeriod Time relative to the message sent date until which the location can be updated, in seconds.
  * @param expiresIn  Left time for which the location can be updated, in seconds. updateMessageContent is not sent when this field changes.
  */
final case class MessageLocation(location: Location, livePeriod: Int, expiresIn: Int) extends MessageContent

private[api] object MessageLocation {
  def fromJava(o: TdApi.MessageLocation): MessageLocation =
    MessageLocation(Location.fromJava(o.location), o.livePeriod, o.expiresIn)
}

/**
  * A message with information about a venue.
  *
  * @param venue The venue description.
  */
final case class MessageVenue(venue: Venue) extends MessageContent

private[api] object MessageVenue {
  def fromJava(o: TdApi.MessageVenue): MessageVenue = MessageVenue(Venue.fromJava(o.venue))
}

/**
  * A message with a user contact.
  *
  * @param contact The contact description.
  */
final case class MessageContact(contact: Contact) extends MessageContent

private[api] object MessageContact {
  def fromJava(o: TdApi.MessageContact): MessageContact = MessageContact(Contact.fromJava(o.contact))
}

/**
  * A message with a game.
  *
  * @param game The game description.
  */
final case class MessageGame(game: Game) extends MessageContent

private[api] object MessageGame {
  def fromJava(o: TdApi.MessageGame): MessageGame = MessageGame(Game.fromJava(o.game))
}

/**
  * A message with a poll.
  *
  * @param poll The poll description.
  */
final case class MessagePoll(poll: Poll) extends MessageContent

private[api] object MessagePoll {
  def fromJava(o: TdApi.MessagePoll): MessagePoll = MessagePoll(Poll.fromJava(o.poll))
}

/**
  * A message with an invoice from a bot.
  *
  * @param title               Product title.
  * @param description         Product description.
  * @param photo               Product photo; may be null.
  * @param currency            Currency for the product price.
  * @param totalAmount         Product total price in the minimal quantity of the currency.
  * @param startParameter      Unique invoice bot startParameter. To share an invoice use the URL https://t.me/{botUsername}?start={startParameter}.
  * @param isTest              True, if the invoice is a test invoice.
  * @param needShippingAddress True, if the shipping address should be specified.
  * @param receiptMessageId    The identifier of the message with the receipt, after the product has been purchased.
  */
final case class MessageInvoice(
  title: String,
  description: String,
  photo: Photo,
  currency: String,
  totalAmount: Long,
  startParameter: String,
  isTest: Boolean,
  needShippingAddress: Boolean,
  receiptMessageId: Long
) extends MessageContent

private[api] object MessageInvoice {
  def fromJava(o: TdApi.MessageInvoice): MessageInvoice =
    MessageInvoice(
      o.title,
      o.description,
      Photo.fromJava(o.photo),
      o.currency,
      o.totalAmount,
      o.startParameter,
      o.isTest,
      o.needShippingAddress,
      o.receiptMessageId
    )
}

/**
  * A message with information about an ended call.
  *
  * @param discardReason Reason why the call was discarded.
  * @param duration      Call duration, in seconds.
  */
final case class MessageCall(discardReason: CallDiscardReason, duration: Int) extends MessageContent

private[api] object MessageCall {
  def fromJava(o: TdApi.MessageCall): MessageCall = MessageCall(CallDiscardReason.fromJava(o.discardReason), o.duration)
}

/**
  * A newly created basic group.
  *
  * @param title         Title of the basic group.
  * @param memberUserIds User identifiers of members in the basic group.
  */
final case class MessageBasicGroupChatCreate(title: String, memberUserIds: List[Int]) extends MessageContent

private[api] object MessageBasicGroupChatCreate {
  def fromJava(o: TdApi.MessageBasicGroupChatCreate): MessageBasicGroupChatCreate =
    MessageBasicGroupChatCreate(o.title, o.memberUserIds.toList)
}

/**
  * A newly created supergroup or channel.
  *
  * @param title Title of the supergroup or channel.
  */
final case class MessageSupergroupChatCreate(title: String) extends MessageContent

private[api] object MessageSupergroupChatCreate {
  def fromJava(o: TdApi.MessageSupergroupChatCreate): MessageSupergroupChatCreate = MessageSupergroupChatCreate(o.title)
}

/**
  * An updated chat title.
  *
  * @param title New chat title.
  */
final case class MessageChatChangeTitle(title: String) extends MessageContent

private[api] object MessageChatChangeTitle {
  def fromJava(o: TdApi.MessageChatChangeTitle): MessageChatChangeTitle = MessageChatChangeTitle(o.title)
}

/**
  * An updated chat photo.
  *
  * @param photo New chat photo.
  */
final case class MessageChatChangePhoto(photo: Photo) extends MessageContent

private[api] object MessageChatChangePhoto {
  def fromJava(o: TdApi.MessageChatChangePhoto): MessageChatChangePhoto =
    MessageChatChangePhoto(Photo.fromJava(o.photo))
}

/**
  * A deleted chat photo.
  */
case object MessageChatDeletePhoto extends MessageContent

/**
  * New chat members were added.
  *
  * @param memberUserIds User identifiers of the new members.
  */
final case class MessageChatAddMembers(memberUserIds: List[Int]) extends MessageContent

private[api] object MessageChatAddMembers {
  def fromJava(o: TdApi.MessageChatAddMembers): MessageChatAddMembers = MessageChatAddMembers(o.memberUserIds.toList)
}

/**
  * A new member joined the chat by invite link.
  */
case object MessageChatJoinByLink extends MessageContent

/**
  * A chat member was deleted.
  *
  * @param userId User identifier of the deleted chat member.
  */
final case class MessageChatDeleteMember(userId: Int) extends MessageContent

private[api] object MessageChatDeleteMember {
  def fromJava(o: TdApi.MessageChatDeleteMember): MessageChatDeleteMember = MessageChatDeleteMember(o.userId)
}

/**
  * A basic group was upgraded to a supergroup and was deactivated as the result.
  *
  * @param supergroupId Identifier of the supergroup to which the basic group was upgraded.
  */
final case class MessageChatUpgradeTo(supergroupId: Int) extends MessageContent

private[api] object MessageChatUpgradeTo {
  def fromJava(o: TdApi.MessageChatUpgradeTo): MessageChatUpgradeTo = MessageChatUpgradeTo(o.supergroupId)
}

/**
  * A supergroup has been created from a basic group.
  *
  * @param title        Title of the newly created supergroup.
  * @param basicGroupId The identifier of the original basic group.
  */
final case class MessageChatUpgradeFrom(title: String, basicGroupId: Int) extends MessageContent

private[api] object MessageChatUpgradeFrom {
  def fromJava(o: TdApi.MessageChatUpgradeFrom): MessageChatUpgradeFrom =
    MessageChatUpgradeFrom(o.title, o.basicGroupId)
}

/**
  * A message has been pinned.
  *
  * @param messageId Identifier of the pinned message, can be an identifier of a deleted message or 0.
  */
final case class MessagePinMessage(messageId: Long) extends MessageContent

private[api] object MessagePinMessage {
  def fromJava(o: TdApi.MessagePinMessage): MessagePinMessage = MessagePinMessage(o.messageId)
}

/**
  * A screenshot of a message in the chat has been taken.
  */
case object MessageScreenshotTaken extends MessageContent

/**
  * The TTL (Time To Live) setting messages in a secret chat has been changed.
  *
  * @param ttl New TTL.
  */
final case class MessageChatSetTtl(ttl: Int) extends MessageContent

private[api] object MessageChatSetTtl {
  def fromJava(o: TdApi.MessageChatSetTtl): MessageChatSetTtl = MessageChatSetTtl(o.ttl)
}

/**
  * A non-standard action has happened in the chat.
  *
  * @param text Message text to be shown in the chat.
  */
final case class MessageCustomServiceAction(text: String) extends MessageContent

private[api] object MessageCustomServiceAction {
  def fromJava(o: TdApi.MessageCustomServiceAction): MessageCustomServiceAction = MessageCustomServiceAction(o.text)
}

/**
  * A new high score was achieved in a game.
  *
  * @param gameMessageId Identifier of the message with the game, can be an identifier of a deleted message.
  * @param gameId        Identifier of the game; may be different from the games presented in the message with the game.
  * @param score         New score.
  */
final case class MessageGameScore(gameMessageId: Long, gameId: Long, score: Int) extends MessageContent

private[api] object MessageGameScore {
  def fromJava(o: TdApi.MessageGameScore): MessageGameScore = MessageGameScore(o.gameMessageId, o.gameId, o.score)
}

/**
  * A payment has been completed.
  *
  * @param invoiceMessageId Identifier of the message with the corresponding invoice; can be an identifier of a deleted message.
  * @param currency         Currency for the price of the product.
  * @param totalAmount      Total price for the product, in the minimal quantity of the currency.
  */
final case class MessagePaymentSuccessful(invoiceMessageId: Long, currency: String, totalAmount: Long)
    extends MessageContent

private[api] object MessagePaymentSuccessful {
  def fromJava(o: TdApi.MessagePaymentSuccessful): MessagePaymentSuccessful =
    MessagePaymentSuccessful(o.invoiceMessageId, o.currency, o.totalAmount)
}

/**
  * A payment has been completed; for bots only.
  *
  * @param invoiceMessageId        Identifier of the message with the corresponding invoice; can be an identifier of a deleted message.
  * @param currency                Currency for price of the product.
  * @param totalAmount             Total price for the product, in the minimal quantity of the currency.
  * @param invoicePayload          Invoice payload.
  * @param shippingOptionId        Identifier of the shipping option chosen by the user; may be empty if not applicable.
  * @param orderInfo               Information about the order; may be None.
  * @param telegramPaymentChargeId Telegram payment identifier.
  * @param providerPaymentChargeId Provider payment identifier.
  */
final case class MessagePaymentSuccessfulBot(
  invoiceMessageId: Long,
  currency: String,
  totalAmount: Long,
  invoicePayload: List[Byte],
  shippingOptionId: String,
  orderInfo: Option[OrderInfo],
  telegramPaymentChargeId: String,
  providerPaymentChargeId: String
) extends MessageContent

private[api] object MessagePaymentSuccessfulBot {
  def fromJava(o: TdApi.MessagePaymentSuccessfulBot): MessagePaymentSuccessfulBot =
    MessagePaymentSuccessfulBot(
      o.invoiceMessageId,
      o.currency,
      o.totalAmount,
      o.invoicePayload.toList,
      o.shippingOptionId,
      Option(o.orderInfo).map(OrderInfo.fromJava),
      o.telegramPaymentChargeId,
      o.providerPaymentChargeId
    )
}

/**
  * A contact has registered with Telegram.
  */
case object MessageContactRegistered extends MessageContent

/**
  * The current user has connected a website by logging in using Telegram Login Widget on it.
  *
  * @param domainName Domain name of the connected website.
  */
final case class MessageWebsiteConnected(domainName: String) extends MessageContent

private[api] object MessageWebsiteConnected {
  def fromJava(o: TdApi.MessageWebsiteConnected): MessageWebsiteConnected = MessageWebsiteConnected(o.domainName)
}

/**
  * Telegram Passport data has been sent.
  *
  * @param types List of Telegram Passport element types sent.
  */
final case class MessagePassportDataSent(types: List[PassportElementType]) extends MessageContent

private[api] object MessagePassportDataSent {
  def fromJava(o: TdApi.MessagePassportDataSent): MessagePassportDataSent =
    MessagePassportDataSent(o.types.map(PassportElementType.fromJava).toList)
}

/**
  * Telegram Passport data has been received; for bots only.
  *
  * @param elements    List of received Telegram Passport elements.
  * @param credentials Encrypted data credentials.
  */
final case class MessagePassportDataReceived(
  elements: List[EncryptedPassportElement],
  credentials: EncryptedCredentials
) extends MessageContent

private[api] object MessagePassportDataReceived {
  def fromJava(o: TdApi.MessagePassportDataReceived): MessagePassportDataReceived =
    MessagePassportDataReceived(
      o.elements.map(EncryptedPassportElement.fromJava).toList,
      EncryptedCredentials.fromJava(o.credentials)
    )
}

/**
  * Message content that is not supported by the client.
  */
case object MessageUnsupported extends MessageContent

private[api] object MessageContent {
  def fromJava(o: TdApi.MessageContent): MessageContent = o.getConstructor match {
    case TdApi.MessageUnsupported.CONSTRUCTOR       => MessageUnsupported
    case TdApi.MessageExpiredPhoto.CONSTRUCTOR      => MessageExpiredPhoto
    case TdApi.MessageExpiredVideo.CONSTRUCTOR      => MessageExpiredVideo
    case TdApi.MessageChatJoinByLink.CONSTRUCTOR    => MessageChatJoinByLink
    case TdApi.MessageChatDeletePhoto.CONSTRUCTOR   => MessageChatDeletePhoto
    case TdApi.MessageScreenshotTaken.CONSTRUCTOR   => MessageScreenshotTaken
    case TdApi.MessageContactRegistered.CONSTRUCTOR => MessageContactRegistered
    case TdApi.MessageText.CONSTRUCTOR              => MessageText.fromJava(o.asInstanceOf[TdApi.MessageText])
    case TdApi.MessageAnimation.CONSTRUCTOR         => MessageAnimation.fromJava(o.asInstanceOf[TdApi.MessageAnimation])
    case TdApi.MessageAudio.CONSTRUCTOR             => MessageAudio.fromJava(o.asInstanceOf[TdApi.MessageAudio])
    case TdApi.MessageDocument.CONSTRUCTOR          => MessageDocument.fromJava(o.asInstanceOf[TdApi.MessageDocument])
    case TdApi.MessagePhoto.CONSTRUCTOR             => MessagePhoto.fromJava(o.asInstanceOf[TdApi.MessagePhoto])
    case TdApi.MessageSticker.CONSTRUCTOR           => MessageSticker.fromJava(o.asInstanceOf[TdApi.MessageSticker])
    case TdApi.MessageVideo.CONSTRUCTOR             => MessageVideo.fromJava(o.asInstanceOf[TdApi.MessageVideo])
    case TdApi.MessageVideoNote.CONSTRUCTOR         => MessageVideoNote.fromJava(o.asInstanceOf[TdApi.MessageVideoNote])
    case TdApi.MessageVoiceNote.CONSTRUCTOR         => MessageVoiceNote.fromJava(o.asInstanceOf[TdApi.MessageVoiceNote])
    case TdApi.MessageLocation.CONSTRUCTOR          => MessageLocation.fromJava(o.asInstanceOf[TdApi.MessageLocation])
    case TdApi.MessageVenue.CONSTRUCTOR             => MessageVenue.fromJava(o.asInstanceOf[TdApi.MessageVenue])
    case TdApi.MessageContact.CONSTRUCTOR           => MessageContact.fromJava(o.asInstanceOf[TdApi.MessageContact])
    case TdApi.MessageGame.CONSTRUCTOR              => MessageGame.fromJava(o.asInstanceOf[TdApi.MessageGame])
    case TdApi.MessagePoll.CONSTRUCTOR              => MessagePoll.fromJava(o.asInstanceOf[TdApi.MessagePoll])
    case TdApi.MessageInvoice.CONSTRUCTOR           => MessageInvoice.fromJava(o.asInstanceOf[TdApi.MessageInvoice])
    case TdApi.MessageCall.CONSTRUCTOR              => MessageCall.fromJava(o.asInstanceOf[TdApi.MessageCall])
    case TdApi.MessagePinMessage.CONSTRUCTOR        => MessagePinMessage.fromJava(o.asInstanceOf[TdApi.MessagePinMessage])
    case TdApi.MessageChatSetTtl.CONSTRUCTOR        => MessageChatSetTtl.fromJava(o.asInstanceOf[TdApi.MessageChatSetTtl])
    case TdApi.MessageGameScore.CONSTRUCTOR         => MessageGameScore.fromJava(o.asInstanceOf[TdApi.MessageGameScore])
    case TdApi.MessageBasicGroupChatCreate.CONSTRUCTOR =>
      MessageBasicGroupChatCreate.fromJava(o.asInstanceOf[TdApi.MessageBasicGroupChatCreate])
    case TdApi.MessageSupergroupChatCreate.CONSTRUCTOR =>
      MessageSupergroupChatCreate.fromJava(o.asInstanceOf[TdApi.MessageSupergroupChatCreate])
    case TdApi.MessageChatChangeTitle.CONSTRUCTOR =>
      MessageChatChangeTitle.fromJava(o.asInstanceOf[TdApi.MessageChatChangeTitle])
    case TdApi.MessageChatChangePhoto.CONSTRUCTOR =>
      MessageChatChangePhoto.fromJava(o.asInstanceOf[TdApi.MessageChatChangePhoto])
    case TdApi.MessageChatAddMembers.CONSTRUCTOR =>
      MessageChatAddMembers.fromJava(o.asInstanceOf[TdApi.MessageChatAddMembers])
    case TdApi.MessageChatDeleteMember.CONSTRUCTOR =>
      MessageChatDeleteMember.fromJava(o.asInstanceOf[TdApi.MessageChatDeleteMember])
    case TdApi.MessageChatUpgradeTo.CONSTRUCTOR =>
      MessageChatUpgradeTo.fromJava(o.asInstanceOf[TdApi.MessageChatUpgradeTo])
    case TdApi.MessageChatUpgradeFrom.CONSTRUCTOR =>
      MessageChatUpgradeFrom.fromJava(o.asInstanceOf[TdApi.MessageChatUpgradeFrom])
    case TdApi.MessageCustomServiceAction.CONSTRUCTOR =>
      MessageCustomServiceAction.fromJava(o.asInstanceOf[TdApi.MessageCustomServiceAction])
    case TdApi.MessagePaymentSuccessful.CONSTRUCTOR =>
      MessagePaymentSuccessful.fromJava(o.asInstanceOf[TdApi.MessagePaymentSuccessful])
    case TdApi.MessagePaymentSuccessfulBot.CONSTRUCTOR =>
      MessagePaymentSuccessfulBot.fromJava(o.asInstanceOf[TdApi.MessagePaymentSuccessfulBot])
    case TdApi.MessageWebsiteConnected.CONSTRUCTOR =>
      MessageWebsiteConnected.fromJava(o.asInstanceOf[TdApi.MessageWebsiteConnected])
    case TdApi.MessagePassportDataSent.CONSTRUCTOR =>
      MessagePassportDataSent.fromJava(o.asInstanceOf[TdApi.MessagePassportDataSent])
    case TdApi.MessagePassportDataReceived.CONSTRUCTOR =>
      MessagePassportDataReceived.fromJava(o.asInstanceOf[TdApi.MessagePassportDataReceived])
  }
}
