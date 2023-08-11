import org.gradle.api.Project

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
}