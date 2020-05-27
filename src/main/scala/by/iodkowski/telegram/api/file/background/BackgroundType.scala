package by.iodkowski.telegram.api.file.background

import org.drinkless.tdlib.TdApi

/**
  * Describes the type of a background.
  */
sealed abstract class BackgroundType extends Product with Serializable

/**
  * A wallpaper in JPEG format.
  *
  * @param isBlurred True, if the wallpaper must be downscaled to fit in 450x450 square and then box-blurred with radius 12.
  * @param isMoving  True, if the background needs to be slightly moved when device is tilted.
  */
final case class BackgroundTypeWallpaper(isBlurred: Boolean, isMoving: Boolean) extends BackgroundType

private[api] object BackgroundTypeWallpaper {
  def fromJava(o: TdApi.BackgroundTypeWallpaper): BackgroundTypeWallpaper =
    BackgroundTypeWallpaper(o.isBlurred, o.isMoving)
}

/**
  * A PNG or TGV (gzipped subset of SVG with MIME type &quot;application/x-tgwallpattern&quot;) pattern to be combined with the background fill chosen by the user.
  *
  * @param fill      Description of the background fill.
  * @param intensity Intensity of the pattern when it is shown above the filled background, 0-100.
  * @param isMoving  True, if the background needs to be slightly moved when device is tilted.
  */
final case class BackgroundTypePattern(fill: BackgroundFill, intensity: Int, isMoving: Boolean) extends BackgroundType

private[api] object BackgroundTypePattern {
  def fromJava(o: TdApi.BackgroundTypePattern): BackgroundTypePattern =
    BackgroundTypePattern(BackgroundFill.fromJava(o.fill), o.intensity, o.isMoving)
}

private[api] object BackgroundType {
  def fromJava(o: TdApi.BackgroundType): BackgroundType = o.getConstructor match {
    case TdApi.BackgroundTypeWallpaper.CONSTRUCTOR =>
      BackgroundTypeWallpaper.fromJava(o.asInstanceOf[TdApi.BackgroundTypeWallpaper])
    case TdApi.BackgroundTypePattern.CONSTRUCTOR =>
      BackgroundTypePattern.fromJava(o.asInstanceOf[TdApi.BackgroundTypePattern])
  }
}
