import sbt._

object Dependencies {

  object Http4s {
    private val Version = "0.21.4"
    lazy val DSL        = "org.http4s" %% "http4s-dsl" % Version
    lazy val Server     = "org.http4s" %% "http4s-blaze-server" % Version
  }

  object Fs2 {
    private val Version = "2.2.1"
    lazy val Core       = "co.fs2" %% "fs2-core" % Version
    lazy val IO         = "co.fs2" %% "fs2-io" % Version
  }

  lazy val PureConfig     = "com.github.pureconfig" %% "pureconfig"            % "0.12.3"
  lazy val DerevoMagnolia = "org.manatki"           %% "derevo-circe-magnolia" % "0.11.3"
  lazy val CirceParser    = "io.circe"              %% "circe-parser"          % "0.13.0"
  lazy val Logback        = "ch.qos.logback"        % "logback-classic"        % "1.2.3"
  lazy val Skunk          = "org.tpolecat"          %% "skunk-core"            % "0.0.9"
}
