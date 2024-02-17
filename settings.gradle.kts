pluginManagement {
  includeBuild("build-logic")
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
    // mavenCentral 快照仓库
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
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
    // mavenCentral 快照仓库
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    jcenter() // 部分依赖需要
  }
}
rootProject.name = "AndroidWheel"
include(":app")

// base
include(":base:all")
include(":base:databinding")
include(":base:ui")

// extensions
include(":extensions:all")
include(":extensions:rxpermissions")
include(":extensions:rxjava")
include(":extensions:gson")
include(":extensions:android")

// utils
include(":utils:impl")
include(":utils:all")
include(":utils:adapter")
include(":utils:view")
include(":utils:context")

// jvm
include(":jvm:impl")
include(":jvm:all")
include(":jvm:flow")
include(":jvm:exception")
include(":jvm:rxjava")
include(":jvm:generics")