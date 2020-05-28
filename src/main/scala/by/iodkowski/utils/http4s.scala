package by.iodkowski.utils

import io.circe.Json
import org.http4s.websocket.WebSocketFrame

object http4s {

  implicit final class WebSocketFrameExtension(val wsf: WebSocketFrame.type) extends AnyVal {
    def fromJson(json: Json): WebSocketFrame = WebSocketFrame.Text(json.noSpaces)
  }
}
