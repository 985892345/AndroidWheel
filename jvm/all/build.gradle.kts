plugins {
  id("library-jvm")
  id("publish")
}
publish.artifactId = "jvm-all"
publish.publicationConfig = Publish.MavenPublicationConfig.Jvm

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