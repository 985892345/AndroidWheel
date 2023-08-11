pluginManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
    maven("https://jitpack.io")
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
    mavenLocal()
    jcenter() // 部分依赖需要
  }
}
rootProject.name = "AndroidWheel"
include(":app")
include(":utils:context")
include(":extensions:android")
include(":extensions:rxjava")
include(":extensions:rxpermissions")
include(":extensions:gson")
include(":utils:SimpleListAdapter")
