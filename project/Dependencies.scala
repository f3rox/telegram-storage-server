import sbt._

object Dependencies {

  object Version {
    lazy val http4s = "0.21.4"
  }

  object Http4s {
    lazy val DSL    = "org.http4s" %% "http4s-dsl"          % Version.http4s
    lazy val Server = "org.http4s" %% "http4s-blaze-server" % Version.http4s
  }

  lazy val PureConfig = "com.github.pureconfig" %% "pureconfig" % "0.12.3"
  lazy val FS2        = "co.fs2"                %% "fs2-core"   % "2.2.1"
}
