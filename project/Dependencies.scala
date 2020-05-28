import sbt._

object Dependencies {

  object Version {
    lazy val Http4s         = "0.21.4"
    lazy val Fs2            = "2.2.1"
    lazy val PureConfig     = "0.12.3"
    lazy val Scache         = "2.2.1"
    lazy val DerevoMagnolia = "0.11.3"
    lazy val Circe          = "0.13.0"
  }

  object Http4s {
    lazy val DSL    = "org.http4s" %% "http4s-dsl"          % Version.Http4s
    lazy val Server = "org.http4s" %% "http4s-blaze-server" % Version.Http4s
  }

  lazy val PureConfig     = "com.github.pureconfig" %% "pureconfig"            % Version.PureConfig
  lazy val Fs2            = "co.fs2"                %% "fs2-core"              % Version.Fs2
  lazy val Scache         = "com.evolutiongaming"   %% "scache"                % Version.Scache
  lazy val DerevoMagnolia = "org.manatki"           %% "derevo-circe-magnolia" % Version.DerevoMagnolia
  lazy val CirceParser    = "io.circe"              %% "circe-parser"          % Version.Circe
}
