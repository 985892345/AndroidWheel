plugins {
  id("library")
  id("publish")
}
publish.artifactId = "utils-adapter"
android.namespace = "com.g985892345.android.utils.adapter"

dependencies {
  api("androidx.recyclerview:recyclerview:1.3.2")
  api("androidx.viewpager2:viewpager2:1.0.0")
  implementation(project(":jvm:generics"))
}
