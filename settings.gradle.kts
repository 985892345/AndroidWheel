pluginManagement {
  includeBuild("build-logic")
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

// base
include(":base:databinding")
include(":base:ui")

// extensions
include(":extensions:rxpermissions")
include(":extensions:rxjava")
include(":extensions:gson")
include(":extensions:android")

// utils
include(":utils:generics")
include(":utils:adapter")
include(":utils:view")
include(":utils:context")

// jvm
include(":jvm:generics")