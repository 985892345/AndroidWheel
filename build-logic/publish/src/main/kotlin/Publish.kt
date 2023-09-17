import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get

/**
 * ...
 *
 * @author 985892345 (Guo Xiangrui)
 * @date 2022/10/10 16:57
 */
abstract class Publish(project: Project) {
  var artifactId: String = ""
  var githubName: String = "AndroidWheel"
  var mainBranch: String = "master"
  var description = "Android 的各种扩展，提供依赖用于快速搭建项目"
  var groupId = project.properties.getValue("GROUP").toString()
  var version = project.properties.getValue("VERSION").toString()

  // 默认选择 Android 依赖
  var publicationConfig : MavenPublicationConfig = MavenPublicationConfig.Android

  sealed interface MavenPublicationConfig {
    fun MavenPublication.configMaven(project: Project)

    object Android : MavenPublicationConfig {
      override fun MavenPublication.configMaven(project: Project) {
        // 在 library.gradle.kts 中设置 release
        from(project.components["release"])
      }
    }

    object Jvm : MavenPublicationConfig {
      override fun MavenPublication.configMaven(project: Project) {
        // 在 jvm.gradle.kts 中设置 java
        artifact(project.tasks["javadocJar"])
        artifact(project.tasks["sourcesJar"])
        from(project.components["java"])
      }
    }
  }
}