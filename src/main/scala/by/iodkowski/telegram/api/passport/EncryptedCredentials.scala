package by.iodkowski.telegram.api.passport

import org.drinkless.tdlib.TdApi

/**
  * Contains encrypted Telegram Passport data credentials.
  *
  * @param data   The encrypted credentials.
  * @param hash   The decrypted data hash.
  * @param secret Secret for data decryption, encrypted with the service's public key.
  */
final case class EncryptedCredentials(data: List[Byte], hash: List[Byte], secret: List[Byte])

private[api] object EncryptedCredentials {
  def fromJava(o: TdApi.EncryptedCredentials): EncryptedCredentials =
    EncryptedCredentials(o.data.toList, o.hash.toList, o.secret.toList)
}
