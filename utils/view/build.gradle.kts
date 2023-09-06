plugins {
  id("library")
  id("publish")
}
publish.artifactId = "utils-view"
android.namespace = "com.g985892345.android.utils.view"

dependencies {
  api(project(":utils:context"))
}

