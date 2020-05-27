package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Describes an address.
  *
  * @param countryCode A two-letter ISO 3166-1 alpha-2 country code.
  * @param state       State, if applicable.
  * @param city        City.
  * @param streetLine1 First line of the address.
  * @param streetLine2 Second line of the address.
  * @param postalCode  Address postal code.
  */
final case class Address(
  countryCode: String,
  state: String,
  city: String,
  streetLine1: String,
  streetLine2: String,
  postalCode: String
)

private[api] object Address {
  def fromJava(o: TdApi.Address): Address =
    Address(o.countryCode, o.state, o.city, o.streetLine1, o.streetLine2, o.postalCode)
}
