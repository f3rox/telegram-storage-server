package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Describes a location on planet Earth.
  *
  * @param latitude  Latitude of the location in degrees; as defined by the sender.
  * @param longitude Longitude of the location, in degrees; as defined by the sender.
  */
final case class Location(latitude: Double, longitude: Double)

private[api] object Location {
  def fromJava(o: TdApi.Location): Location = Location(o.latitude, o.longitude)
}
