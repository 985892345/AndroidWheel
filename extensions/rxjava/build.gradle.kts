plugins {
  id("library")
}
publisher.artifactId = "extensions-rxjava"
android.namespace = "com.g985892345.android.extensions.rxjava"

dependencies {
  api(project(":jvm:rxjava"))
  api("io.reactivex.rxjava3:rxandroid:3.0.2")
}

