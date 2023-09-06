plugins {
  id("library")
  id("publish")
}
publish.artifactId = "extensions-gson"
android.namespace = "com.g985892345.android.extensions.gson"

dependencies {
  api("com.google.code.gson:gson:2.10.1")
}

