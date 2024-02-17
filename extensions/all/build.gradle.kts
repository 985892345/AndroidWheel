plugins {
  id("library")
}
publisher.artifactId = "extensions-all"
android.namespace = "com.g985892345.android.extensions.all"

// 依赖 extensions 下的其他模块
val thisProject = project
val thisParent = project.parent!!
dependencies {
  thisParent.subprojects {
    if (thisProject.name != name) {
      api(project(path))
    }
  }
}
