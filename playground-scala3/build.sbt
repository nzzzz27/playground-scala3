ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "3.2.1"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val http4sVersion  = "0.23.16"
lazy val tapirVersion   = "1.2.8"

lazy val root = (project in file("."))
  .settings(
    name := "scala3-app-test",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-ember-client" % http4sVersion,

      "com.softwaremill.sttp.tapir" %% "tapir-core"          % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-http4s-server" % tapirVersion,
      // "org.scalameta" %% "munit" % "0.7.29" % Test,
    )
  )