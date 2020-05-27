package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Order information.
  *
  * @param name            Name of the user.
  * @param phoneNumber     Phone number of the user.
  * @param emailAddress    Email address of the user.
  * @param shippingAddress Shipping address for this order; may be None.
  */
final case class OrderInfo(name: String, phoneNumber: String, emailAddress: String, shippingAddress: Option[Address])

private[api] object OrderInfo {
  def fromJava(o: TdApi.OrderInfo): OrderInfo =
    OrderInfo(o.name, o.phoneNumber, o.emailAddress, Option(o.shippingAddress).map(Address.fromJava))
}
