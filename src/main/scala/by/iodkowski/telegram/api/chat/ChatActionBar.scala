package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi

/**
  * Describes actions which should be possible to do through a chat action bar.
  */
sealed abstract class ChatActionBar extends Product with Serializable

/**
  * The chat can be reported as spam using the method reportChat with the reason chatReportReasonSpam.
  */
case object ChatActionBarReportSpam extends ChatActionBar

/**
  * The chat is a location-based supergroup, which can be reported as having unrelated location using the method reportChat with the reason chatReportReasonUnrelatedLocation.
  */
case object ChatActionBarReportUnrelatedLocation extends ChatActionBar

/**
  * The chat is a private or secret chat, which can be reported using the method reportChat, or the other user can be added to the contact list using the method addContact, or the other user can be blocked using the method blockUser.
  */
case object ChatActionBarReportAddBlock extends ChatActionBar

/**
  * The chat is a private or secret chat and the other user can be added to the contact list using the method addContact.
  */
case object ChatActionBarAddContact extends ChatActionBar

/**
  * The chat is a private or secret chat with a mutual contact and the user's phone number can be shared with the other user using the method sharePhoneNumber.
  */
case object ChatActionBarSharePhoneNumber extends ChatActionBar

private[api] object ChatActionBar {
  def fromJava(o: TdApi.ChatActionBar): ChatActionBar = o.getConstructor match {
    case TdApi.ChatActionBarReportSpam.CONSTRUCTOR              => ChatActionBarReportSpam
    case TdApi.ChatActionBarReportUnrelatedLocation.CONSTRUCTOR => ChatActionBarReportUnrelatedLocation
    case TdApi.ChatActionBarReportAddBlock.CONSTRUCTOR          => ChatActionBarReportAddBlock
    case TdApi.ChatActionBarAddContact.CONSTRUCTOR              => ChatActionBarAddContact
    case TdApi.ChatActionBarSharePhoneNumber.CONSTRUCTOR        => ChatActionBarSharePhoneNumber
  }
}
