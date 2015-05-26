name := """entertainer"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "org.mongodb" %% "casbah" % "2.8.1",
  "com.novus" %% "salat" % "1.9.9",
  ws
)
