plugins {
  id("library")
  id("publish")
}
publish.artifactId = "base-databinding"
android.namespace = "com.g985892345.android.base.databinding"

dependencies {
  api(project(":base:ui"))
  api(project(":jvm:generics"))

  api("androidx.databinding:databinding-runtime:${libs.versions.androidGradlePlugin.get()}")
}