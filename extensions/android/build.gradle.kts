plugins {
  id("library")
  id("publish")
}
publish.artifactId = "extensions-android"
android.namespace = "com.g985892345.android.extensions.android"

dependencies {
  api(project(":utils:context"))
  // optional - ProcessLifecycleOwner provides a lifecycle for the whole application process
  api("androidx.lifecycle:lifecycle-process:2.6.2")
}

