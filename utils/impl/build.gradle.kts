plugins {
  id("library")
  id("publish")
}
publish.artifactId = "utils-impl"
android.namespace = "com.g985892345.android.utils.impl"

dependencies {
  api(project(":jvm:impl"))
}