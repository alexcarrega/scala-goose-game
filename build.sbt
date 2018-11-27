scalaVersion := "2.12.7"

onLoadMessage := ""
showSuccess := false
logLevel in run := Level.Error

name := "scala-googe-game"
organization := "com.alexcarrega"
version := "1.0.0"

libraryDependencies += "org.scala-sbt" % "sbt" % "1.2.6"
libraryDependencies += "org.typelevel" %% "cats-core" % "1.4.0"
libraryDependencies += "org.scala-sbt" %% "command" % "1.2.6"
libraryDependencies += "com.lihaoyi" %% "fansi" % "0.2.5"
