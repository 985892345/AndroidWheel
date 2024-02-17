plugins {
  id("library")
}
publisher.artifactId = "utils-all"
android.namespace = "com.g985892345.android.utils.all"

// 依赖 utils 下的其他模块
val thisProject = project
val thisParent = project.parent!!
dependencies {
  thisParent.subprojects {
    if (thisProject.name != name) {
      api(project(path))
    }
  }
}