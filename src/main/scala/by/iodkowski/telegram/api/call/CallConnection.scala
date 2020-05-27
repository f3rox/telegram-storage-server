package by.iodkowski.telegram.api.call

import org.drinkless.tdlib.TdApi

/**
  * Describes the address of UDP reflectors.
  *
  * @param id      Reflector identifier.
  * @param ip      IPv4 reflector address.
  * @param ipv6    IPv6 reflector address.
  * @param port    Reflector port number.
  * @param peerTag Connection peer tag.
  */
final case class CallConnection(id: Long, ip: String, ipv6: String, port: Int, peerTag: List[Byte])

private[api] object CallConnection {
  def fromJava(o: TdApi.CallConnection): CallConnection = CallConnection(o.id, o.ip, o.ipv6, o.port, o.peerTag.toList)
}
