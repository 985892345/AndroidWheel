plugins {
  id("library-jvm")
  id("publish")
}
publish.artifactId = "jvm-rxjava"
publish.publicationConfig = Publish.MavenPublicationConfig.Jvm

dependencies {
  api(project(":jvm:exception"))
  api("io.reactivex.rxjava3:rxjava:3.1.6")
}
