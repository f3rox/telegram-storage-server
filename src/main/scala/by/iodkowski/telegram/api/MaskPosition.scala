package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Position on a photo where a mask should be placed.
  *
  * @param point  Part of the face, relative to which the mask should be placed.
  * @param xShift Shift by X-axis measured in widths of the mask scaled to the face size, from left to right. (For example, -1.0 will place the mask just to the left of the default mask position.)
  * @param yShift Shift by Y-axis measured in heights of the mask scaled to the face size, from top to bottom. (For example, 1.0 will place the mask just below the default mask position.)
  * @param scale  Mask scaling coefficient. (For example, 2.0 means a doubled size.)
  */
final case class MaskPosition(point: MaskPoint, xShift: Double, yShift: Double, scale: Double)

private[api] object MaskPosition {
  def fromJava(o: TdApi.MaskPosition): MaskPosition =
    MaskPosition(MaskPoint.fromJava(o.point), o.xShift, o.yShift, o.scale)
}
