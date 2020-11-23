
val Repositories = Seq(
  "Typesafe repository"           at "https://repo.typesafe.com/typesafe/releases/",
  "Sonatype OSS Snapshots"        at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases"         at "https://oss.sonatype.org/content/repositories/releases",
  "scalaz-bintray"                at "https://dl.bintray.com/scalaz/releases"
)

val commonSettings = Seq(
  organization := "com.eclipsesource",
  scalaVersion := "2.13.0",
  crossScalaVersions := Seq("2.12.12", "2.13.0"),
  licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  Keys.fork in Test := false,
  Keys.parallelExecution in Test := false
)

val releaseSettings = Seq(
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://nexus.waylay.io"
    if (isSnapshot.value)
      Some("Waylay snapshot repo" at nexus + "/repository/maven-snapshots")
    else
      Some("Waylay releases repo" at nexus + "/repository/maven-releases")
  }
)

val buildSettings = Defaults.coreDefaultSettings ++ commonSettings

val testSettings = unmanagedJars in Test ++= Seq(
  baseDirectory.value / "src/test/resources/simple-schema.jar",
  baseDirectory.value / "src/test/resources/simple-schema-issue-65.jar",
  baseDirectory.value / "src/test/resources/issue-65.jar"
)

lazy val schemaProject = Project("play-json-schema-validator", file("."))
  .settings(buildSettings)
  .settings(releaseSettings)
  .settings(testSettings)
  .settings(
    resolvers ++= Repositories,
    retrieveManaged := true,
    libraryDependencies ++= Dependencies.core,
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
  )

