package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Describes a venue.
  *
  * @param location Venue location; as defined by the sender.
  * @param title    Venue name; as defined by the sender.
  * @param address  Venue address; as defined by the sender.
  * @param provider Provider of the venue database; as defined by the sender. Currently only &quot;foursquare&quot; needs to be supported.
  * @param id       Identifier of the venue in the provider database; as defined by the sender.
  * @param type     Type of the venue in the provider database; as defined by the sender.
  */
final case class Venue(location: Location, title: String, address: String, provider: String, id: String, `type`: String)

private[api] object Venue {
  def fromJava(o: TdApi.Venue): Venue =
    Venue(Location.fromJava(o.location), o.title, o.address, o.provider, o.id, o.`type`)
}
