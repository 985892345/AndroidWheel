plugins {
  id("library-jvm")
}
publisher.artifactId = "jvm-rxjava"

dependencies {
  api(project(":jvm:exception"))
  api("io.reactivex.rxjava3:rxjava:3.1.8")
}
