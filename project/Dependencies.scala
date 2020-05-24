import sbt._

object Dependencies {

  object Http4s {
    private lazy val version = "0.21.4"

    lazy val DSL    = "org.http4s" %% "http4s-dsl"          % version
    lazy val Server = "org.http4s" %% "http4s-blaze-server" % version
  }

  lazy val PureConfig = "com.github.pureconfig" %% "pureconfig" % "0.12.3"
}
