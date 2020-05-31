import Dependencies._
import sbt._

lazy val root =
  (project in file("."))
    .settings(
      name := "telegram-storage-server",
      scalaVersion := "2.13.2",
      scalacOptions += "-Ymacro-annotations",
      libraryDependencies ++= Seq(
        Http4s.DSL,
        Http4s.Server,
        PureConfig,
        Fs2.Core,
        Fs2.IO,
        DerevoMagnolia,
        CirceParser,
        Logback,
        Skunk
      )
    )
