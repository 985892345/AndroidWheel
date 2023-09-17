plugins {
  id("library")
  id("publish")
}
publish.artifactId = "extensions-rxpermissions"
android.namespace = "com.g985892345.android.extensions.rxpermissions"

dependencies {
  api(project(":jvm:rxjava"))
  api("com.github.tbruyelle:rxpermissions:0.12")
}

