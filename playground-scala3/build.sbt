ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "3.2.1"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val http4sVersion  = "0.23.16"

lazy val root = (project in file("."))
  .enablePlugins(SbtTwirl)
  .settings(
    name := "scala3-app-test",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-ember-client" % http4sVersion,

      "com.softwaremill.sttp.tapir" %% "tapir-core" % "1.2.7",
      // "org.scalameta" %% "munit" % "0.7.29" % Test,
    )
  )