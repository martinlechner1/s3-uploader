import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

addSbtPlugin("org.lyranthe.sbt" % "partial-unification" % "1.1.2")
scalacOptions += "-Ypartial-unification"
val aws4sVersion = "0.6.2"
lazy val root = (project in file("."))
  .settings(
    name := "s3uploader",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "1.2.0",
      "org.typelevel" %% "cats-core" % "1.6.0",
      "software.amazon.awssdk" % "s3" % "2.4.5",
      scalaTest % Test
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
