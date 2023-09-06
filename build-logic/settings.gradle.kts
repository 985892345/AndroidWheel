pluginManagement {
  includeBuild(".")
  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
  }
}

dependencyResolutionManagement {
  repositories {
    mavenCentral()
    google()
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
    maven("https://jitpack.io")
  }
  // 开启 versionCatalogs 功能
  versionCatalogs {
    // 名称固定写成 libs
    create("libs") {
      from(files("../gradle/libs.versions.toml"))
    }
  }
}
rootProject.name = "build-logic"

include("library")
include("publish")
