plugins {
  id("library")
  id("publish")
}
publish.artifactId = "base-databinding"
android.namespace = "com.g985892345.android.base.databinding"

android.buildFeatures.dataBinding = true

dependencies {
  api(project(":base:ui"))
  api(project(":jvm:generics"))
}