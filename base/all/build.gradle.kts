plugins {
  id("library")
  id("publish")
}
publish.artifactId = "base-all"
android.namespace = "com.g985892345.android.base.all"

// 依赖 all 下的其他模块
val thisProject = project
val thisParent = project.parent!!
dependencies {
  thisParent.subprojects {
    if (thisProject.name != name) {
      api(project(path))
    }
  }
}
