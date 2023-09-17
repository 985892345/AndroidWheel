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
include(":utils:all")
include(":utils:adapter")
include(":utils:view")
include(":utils:context")

// jvm
include(":jvm:all")
include(":jvm:flow")
include(":jvm:exception")
include(":jvm:rxjava")
include(":jvm:generics")