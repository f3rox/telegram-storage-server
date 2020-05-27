package by.iodkowski.telegram.api.call

import org.drinkless.tdlib.TdApi

/**
  * Specifies the supported call protocols.
  *
  * @param udpP2p       True, if UDP peer-to-peer connections are supported.
  * @param udpReflector True, if connection through UDP reflectors is supported.
  * @param minLayer     The minimum supported API layer; use 65.
  * @param maxLayer     The maximum supported API layer; use 65.
  */
final case class CallProtocol(udpP2p: Boolean, udpReflector: Boolean, minLayer: Int, maxLayer: Int)

private[api] object CallProtocol {
  def fromJava(o: TdApi.CallProtocol): CallProtocol = CallProtocol(o.udpP2p, o.udpReflector, o.minLayer, o.maxLayer)
}
