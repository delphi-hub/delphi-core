name := "delphi-client"

libraryDependencies += "joda-time" % "joda-time" % "2.10.5"


libraryDependencies ++= Seq(
  "com.softwaremill.sttp" %% "core" % "1.7.2",
  "com.softwaremill.sttp" %% "spray-json" % "1.7.2"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"