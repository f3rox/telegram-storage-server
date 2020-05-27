package by.iodkowski.telegram.api.passport

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.file.DatedFile

/**
  * Contains information about an encrypted Telegram Passport element; for bots only.
  *
  * @param type        Type of Telegram Passport element.
  * @param data        Encrypted JSON-encoded data about the user.
  * @param frontSide   The front side of an identity document.
  * @param reverseSide The reverse side of an identity document; may be None.
  * @param selfie      Selfie with the document; may be None.
  * @param translation List of files containing a certified English translation of the document.
  * @param files       List of attached files.
  * @param value       Unencrypted data, phone number or email address.
  * @param hash        Hash of the entire element.
  */
final case class EncryptedPassportElement(
  `type`: PassportElementType,
  data: List[Byte],
  frontSide: DatedFile,
  reverseSide: Option[DatedFile],
  selfie: Option[DatedFile],
  translation: List[DatedFile],
  files: List[DatedFile],
  value: String,
  hash: String
)

private[api] object EncryptedPassportElement {
  def fromJava(o: TdApi.EncryptedPassportElement): EncryptedPassportElement =
    EncryptedPassportElement(
      PassportElementType.fromJava(o.`type`),
      o.data.toList,
      DatedFile.fromJava(o.frontSide),
      Option(o.reverseSide).map(DatedFile.fromJava),
      Option(o.selfie).map(DatedFile.fromJava),
      o.translation.map(DatedFile.fromJava).toList,
      o.files.map(DatedFile.fromJava).toList,
      o.value,
      o.hash
    )
}
