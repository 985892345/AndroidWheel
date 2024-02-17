plugins {
  id("library-jvm")
}
publisher.artifactId = "jvm-all"

// 依赖 jvm 下的其他模块
val thisProject = project
val thisParent = project.parent!!
dependencies {
  thisParent.subprojects {
    if (thisProject.name != name) {
      api(project(path))
    }
  }
}