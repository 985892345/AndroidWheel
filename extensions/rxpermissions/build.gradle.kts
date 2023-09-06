plugins {
  id("library")
  id("publish")
}
publish.artifactId = "extensions-rxpermissions"
android.namespace = "com.g985892345.android.extensions.rxpermissions"

dependencies {
  api("io.reactivex.rxjava3:rxjava:3.1.6")
  api("com.github.tbruyelle:rxpermissions:0.12")
}

