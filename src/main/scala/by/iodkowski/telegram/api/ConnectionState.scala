package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Describes the current state of the connection to Telegram servers.
  */
sealed abstract class ConnectionState extends Product with Serializable

/**
  * Currently waiting for the network to become available. Use setNetworkType to change the available network type.
  */
case object ConnectionStateWaitingForNetwork extends ConnectionState

/**
  * Currently establishing a connection with a proxy server.
  */
case object ConnectionStateConnectingToProxy extends ConnectionState

/**
  * Currently establishing a connection to the Telegram servers.
  */
case object ConnectionStateConnecting extends ConnectionState

/**
  * Downloading data received while the client was offline.
  */
case object ConnectionStateUpdating extends ConnectionState

/**
  * There is a working connection to the Telegram servers.
  */
case object ConnectionStateReady extends ConnectionState

private[api] object ConnectionState {
  def fromJava(o: TdApi.ConnectionState): ConnectionState = o.getConstructor match {
    case TdApi.ConnectionStateWaitingForNetwork.CONSTRUCTOR => ConnectionStateWaitingForNetwork
    case TdApi.ConnectionStateConnectingToProxy.CONSTRUCTOR => ConnectionStateConnectingToProxy
    case TdApi.ConnectionStateConnecting.CONSTRUCTOR        => ConnectionStateConnecting
    case TdApi.ConnectionStateUpdating.CONSTRUCTOR          => ConnectionStateUpdating
    case TdApi.ConnectionStateReady.CONSTRUCTOR             => ConnectionStateReady
  }
}
