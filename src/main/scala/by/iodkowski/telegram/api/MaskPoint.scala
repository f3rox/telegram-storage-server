package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Part of the face, relative to which a mask should be placed.
  */
sealed abstract class MaskPoint extends Product with Serializable

/**
  * A mask should be placed relatively to the chin.
  */
case object MaskPointChin extends MaskPoint

/**
  * A mask should be placed relatively to the eyes.
  */
case object MaskPointEyes extends MaskPoint

/**
  * A mask should be placed relatively to the forehead.
  */
case object MaskPointForehead extends MaskPoint

/**
  * A mask should be placed relatively to the mouth.
  */
case object MaskPointMouth extends MaskPoint

private[api] object MaskPoint {
  def fromJava(o: TdApi.MaskPoint): MaskPoint = o.getConstructor match {
    case TdApi.MaskPointChin.CONSTRUCTOR     => MaskPointChin
    case TdApi.MaskPointEyes.CONSTRUCTOR     => MaskPointEyes
    case TdApi.MaskPointForehead.CONSTRUCTOR => MaskPointForehead
    case TdApi.MaskPointMouth.CONSTRUCTOR    => MaskPointMouth
  }
}
