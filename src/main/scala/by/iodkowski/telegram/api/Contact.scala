package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Describes a user contact.
  *
  * @param phoneNumber Phone number of the user.
  * @param firstName   First name of the user; 1-255 characters in length.
  * @param lastName    Last name of the user.
  * @param vcard       Additional data about the user in a form of vCard; 0-2048 bytes in length.
  * @param userId      Identifier of the user, if known; otherwise 0.
  */
final case class Contact(phoneNumber: String, firstName: String, lastName: String, vcard: String, userId: Int)

private[api] object Contact {
  def fromJava(o: TdApi.Contact): Contact = Contact(o.phoneNumber, o.firstName, o.lastName, o.vcard, o.userId)
}
