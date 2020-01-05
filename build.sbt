ThisBuild / organization := "de.upb.cs.swt.delphi"
ThisBuild / organizationName := "Delphi Project"
ThisBuild / organizationHomepage := Some(url("https://delphi.cs.uni-paderborn.de/"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/delphi-hub/delphi-core"),
    "scm:git@github.com:delphi-hub/delphi-core.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id    = "bhermann",
    name  = "Ben Hermann",
    email = "ben.hermann@upb.de",
    url   = url("https://www.thewhitespace.de")
  )
)
ThisBuild / description := "Core components for Delphi."
ThisBuild / licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / homepage := Some(url("https://delphi.cs.uni-paderborn.de/"))

ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true

ThisBuild / version      := "0.9.2"

lazy val scala212 = "2.12.10"
lazy val scala213 = "2.13.1"
lazy val supportedScalaVersions = List(scala212, scala213)

ThisBuild / scalaVersion := scala213


lazy val root = (project in file("."))
  .settings (
    crossScalaVersions := Nil,
    publish / skip := true
  )
  .aggregate(core, client)


lazy val core = project
                .settings(
                  crossScalaVersions := supportedScalaVersions,
                  // other settings
                )
lazy val client = project
                .settings(
                  crossScalaVersions := supportedScalaVersions,
                  // other settings
                )
                .dependsOn(core)
