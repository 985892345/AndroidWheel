plugins {
  id("library")
  id("publish")
}
publish.artifactId = "base-ui"
android.namespace = "com.g985892345.android.base.ui"

dependencies {
  // https://developer.android.google.cn/jetpack/androidx/releases/lifecycle
  // https://developer.android.google.cn/kotlin/ktx/extensions-list?hl=zh_cn#androidxlifecycle
  api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
  api("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
  // https://developer.android.google.cn/kotlin/ktx/extensions-list#androidxactivity
  api("androidx.activity:activity-ktx:1.8.0")
  // https://developer.android.google.cn/kotlin/ktx/extensions-list#androidxfragmentapp
  api("androidx.fragment:fragment-ktx:1.6.1")

  api(project(":extensions:android"))
  api(project(":extensions:rxjava"))
  api(project(":utils:view"))
}