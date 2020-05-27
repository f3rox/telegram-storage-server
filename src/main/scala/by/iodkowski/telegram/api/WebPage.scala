package by.iodkowski.telegram.api

import by.iodkowski.telegram.api.file._
import by.iodkowski.telegram.api.file.sticker.Sticker
import org.drinkless.tdlib.TdApi

/**
  * Describes a web page preview.
  *
  * @param url                Original URL of the link.
  * @param displayUrl         URL to display.
  * @param type               Type of the web page. Can be: article, photo, audio, video, document, profile, app, or something else.
  * @param siteName           Short name of the site (e.g., Google Docs, App Store).
  * @param title              Title of the content.
  * @param description        Description of the content.
  * @param photo              Image representing the content; may be None.
  * @param embedUrl           URL to show in the embedded preview.
  * @param embedType          MIME type of the embedded preview, (e.g., text/html or video/mp4).
  * @param embedWidth         Width of the embedded preview.
  * @param embedHeight        Height of the embedded preview.
  * @param duration           Duration of the content, in seconds.
  * @param author             Author of the content.
  * @param animation          Preview of the content as an animation, if available; may be None.
  * @param audio              Preview of the content as an audio file, if available; may be None.
  * @param document           Preview of the content as a document, if available (currently only available for small PDF files and ZIP archives); may be None.
  * @param sticker            Preview of the content as a sticker for small WEBP files, if available; may be None.
  * @param video              Preview of the content as a video, if available; may be None.
  * @param videoNote          Preview of the content as a video note, if available; may be None.
  * @param voiceNote          Preview of the content as a voice note, if available; may be None.
  * @param instantViewVersion Version of instant view, available for the web page (currently can be 1 or 2), 0 if none.
  */
final case class WebPage(
  url: String,
  displayUrl: String,
  `type`: String,
  siteName: String,
  title: String,
  description: String,
  photo: Option[Photo],
  embedUrl: String,
  embedType: String,
  embedWidth: Int,
  embedHeight: Int,
  duration: Int,
  author: String,
  animation: Option[Animation],
  audio: Option[Audio],
  document: Option[Document],
  sticker: Option[Sticker],
  video: Option[Video],
  videoNote: Option[VideoNote],
  voiceNote: Option[VoiceNote],
  instantViewVersion: Int
)

private[api] object WebPage {
  def fromJava(o: TdApi.WebPage): WebPage =
    WebPage(
      o.url,
      o.displayUrl,
      o.`type`,
      o.siteName,
      o.title,
      o.description,
      Option(o.photo).map(Photo.fromJava),
      o.embedUrl,
      o.embedType,
      o.embedWidth,
      o.embedHeight,
      o.duration,
      o.author,
      Option(o.animation).map(Animation.fromJava),
      Option(o.audio).map(Audio.fromJava),
      Option(o.document).map(Document.fromJava),
      Option(o.sticker).map(Sticker.fromJava),
      Option(o.video).map(Video.fromJava),
      Option(o.videoNote).map(VideoNote.fromJava),
      Option(o.voiceNote).map(VoiceNote.fromJava),
      o.instantViewVersion
    )
}
