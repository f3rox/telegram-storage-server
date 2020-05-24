import sbt._
import Dependencies._

lazy val root =
  (project in file("."))
    .settings(
      name := "telegram-storage-server",
      scalaVersion := "2.13.2",
      libraryDependencies ++= Seq(
        Http4s.DSL,
        Http4s.Server,
        PureConfig
      )
    )
