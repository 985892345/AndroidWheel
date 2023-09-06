plugins {
  id("library")
  id("publish")
}
publish.artifactId = "utils-context"
android.namespace = "com.g985892345.android.utils.context"

dependencies {
  api("androidx.startup:startup-runtime:1.1.1")
}

