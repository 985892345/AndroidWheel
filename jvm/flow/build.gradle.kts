plugins {
  id("library-jvm")
}
publisher.artifactId = "jvm-flow"

dependencies {
  api(project(":jvm:exception"))
  api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}