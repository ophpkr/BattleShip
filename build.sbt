ThisBuild / scalaVersion := "2.12.6"
ThisBuild / organization := "com.game"

lazy val battleship = (project in file("."))
  .settings(
    name := "BattleShip",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  )