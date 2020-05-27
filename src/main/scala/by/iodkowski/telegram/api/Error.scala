package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * An object of this type can be returned on every function call, in case of an error.
  *
  * @param code    Error code; subject to future changes. If the error code is 406, the error message must not be processed in any way and must not be displayed to the user.
  * @param message Error message; subject to future changes.
  */
final case class Error(code: Int, message: String)

object Error {
  private[api] def fromJava(o: TdApi.Error): Error = Error(o.code, o.message)
}
