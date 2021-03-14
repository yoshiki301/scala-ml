import slick.codegen.SourceCodeGenerator

name := """scala-server"""
organization := "com.example"

version := "1.0-SNAPSHOT"

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.2.19",
)

lazy val codegen = taskKey[Unit]("generate slick table code")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    codegen := {
      SourceCodeGenerator.main(
        Array(
          "slick.jdbc.PostgresProfile",
          "org.postgresql.Driver",
          "jdbc:postgresql://localhost:5432/postgres",
          "app/infrastructure",
          "infrastructure.dto",
          "root",
          "root",
          "true",
          "slick.codegen.SourceCodeGenerator",
          "true"
        )
      )
    }
  )

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
