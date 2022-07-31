val scala3Version = "3.1.3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "getting-your-feet-wet-with-scala3",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.12" % "test"
    )
  )
