package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Portion of the price of a product (e.g., &quot;delivery cost&quot;, &quot;tax amount&quot;).
  *
  * @param label  Label for this portion of the product price.
  * @param amount Currency amount in minimal quantity of the currency.
  */
final case class LabeledPricePart(label: String, amount: Long)

private[api] object LabeledPricePart {
  def fromJava(o: TdApi.LabeledPricePart): LabeledPricePart = LabeledPricePart(o.label, o.amount)
}
