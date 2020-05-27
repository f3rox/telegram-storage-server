package by.iodkowski.telegram.api.file.background

import org.drinkless.tdlib.TdApi

/**
  * Describes a fill of a background.
  */
sealed abstract class BackgroundFill extends Product with Serializable

/**
  * Describes a solid fill of a background.
  *
  * @param color A color of the background in the RGB24 format.
  */
final case class BackgroundFillSolid(color: Int) extends BackgroundFill

private[api] object BackgroundFillSolid {
  def fromJava(o: TdApi.BackgroundFillSolid): BackgroundFillSolid = BackgroundFillSolid(o.color)
}

/**
  * Describes a gradient fill of a background.
  *
  * @param topColor      A top color of the background in the RGB24 format.
  * @param bottomColor   A bottom color of the background in the RGB24 format.
  * @param rotationAngle Clockwise rotation angle of the gradient, in degrees; 0-359. Should be always divisible by 45.
  */
final case class BackgroundFillGradient(topColor: Int, bottomColor: Int, rotationAngle: Int) extends BackgroundFill

private[api] object BackgroundFillGradient {
  def fromJava(o: TdApi.BackgroundFillGradient): BackgroundFillGradient =
    BackgroundFillGradient(o.topColor, o.bottomColor, o.rotationAngle)
}

private[api] object BackgroundFill {
  def fromJava(o: TdApi.BackgroundFill): BackgroundFill = o.getConstructor match {
    case TdApi.BackgroundFillSolid.CONSTRUCTOR =>
      BackgroundFillSolid.fromJava(o.asInstanceOf[TdApi.BackgroundFillSolid])
    case TdApi.BackgroundFillGradient.CONSTRUCTOR =>
      BackgroundFillGradient.fromJava(o.asInstanceOf[TdApi.BackgroundFillGradient])
  }
}
