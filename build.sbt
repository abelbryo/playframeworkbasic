name := "SuperDuperApp"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "com.typesafe.play" %% "play-slick" % "0.5.0.8",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

play.Project.playScalaSettings
