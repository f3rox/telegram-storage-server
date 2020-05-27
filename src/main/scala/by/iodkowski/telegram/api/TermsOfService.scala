package by.iodkowski.telegram.api

import by.iodkowski.telegram.api.text.FormattedText
import org.drinkless.tdlib.TdApi

/**
  * Contains Telegram terms of service.
  *
  * @param text       Text of the terms of service.
  * @param minUserAge The minimum age of a user to be able to accept the terms; 0 if any.
  * @param showPopup  True, if a blocking popup with terms of service must be shown to the user.
  */
final case class TermsOfService(text: FormattedText, minUserAge: Int, showPopup: Boolean)

private[api] object TermsOfService {
  def fromJava(o: TdApi.TermsOfService): TermsOfService =
    TermsOfService(FormattedText.fromJava(o.text), o.minUserAge, o.showPopup)
}
