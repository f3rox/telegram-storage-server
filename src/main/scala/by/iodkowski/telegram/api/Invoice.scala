package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Product invoice.
  *
  * @param currency                   ISO 4217 currency code.
  * @param priceParts                 A list of objects used to calculate the total price of the product.
  * @param isTest                     True, if the payment is a test payment.
  * @param needName                   True, if the user's name is needed for payment.
  * @param needPhoneNumber            True, if the user's phone number is needed for payment.
  * @param needEmailAddress           True, if the user's email address is needed for payment.
  * @param needShippingAddress        True, if the user's shipping address is needed for payment.
  * @param sendPhoneNumberToProvider  True, if the user's phone number will be sent to the provider.
  * @param sendEmailAddressToProvider True, if the user's email address will be sent to the provider.
  * @param isFlexible                 True, if the total price depends on the shipping method.
  */
final case class Invoice(
  currency: String,
  priceParts: List[LabeledPricePart],
  isTest: Boolean,
  needName: Boolean,
  needPhoneNumber: Boolean,
  needEmailAddress: Boolean,
  needShippingAddress: Boolean,
  sendPhoneNumberToProvider: Boolean,
  sendEmailAddressToProvider: Boolean,
  isFlexible: Boolean
)

private[api] object Invoice {
  def fromJava(o: TdApi.Invoice): Invoice =
    Invoice(
      o.currency,
      o.priceParts.map(LabeledPricePart.fromJava).toList,
      o.isTest,
      o.needName,
      o.needPhoneNumber,
      o.needEmailAddress,
      o.needShippingAddress,
      o.sendPhoneNumberToProvider,
      o.sendEmailAddressToProvider,
      o.isFlexible
    )
}
