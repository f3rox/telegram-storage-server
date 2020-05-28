package by.iodkowski.socket

import derevo._
import derevo.circe.magnolia._
import io.circe.magnolia.configured.Configuration

object SocketProtocol {

  private implicit val config: Configuration =
    Configuration.default.withSnakeCaseConstructorNames.withDiscriminator("type")

  @derive(customizableDecoder)
  sealed trait ClientMessage
  object ClientMessage {
    final case class CheckCode(code: String) extends ClientMessage
    final case class CheckPassword(password: String) extends ClientMessage
    final case object LogOut extends ClientMessage
  }

  @derive(customizableEncoder)
  sealed trait ServerMessage
  object ServerMessage {
    final case object WaitCode extends ServerMessage
    final case object WaitPassword extends ServerMessage
    final case object Ready extends ServerMessage
    final case object Closed extends ServerMessage
    final case class Error(message: String) extends ServerMessage
  }
}
