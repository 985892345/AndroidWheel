pluginManagement {
  includeBuild(".")
  repositories {
    gradlePluginPortal()
    mavenLocal()
    mavenCentral()
    google()
    // mavenCentral 快照仓库
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
  }
}

dependencyResolutionManagement {
  repositories {
    mavenLocal()
    mavenCentral()
    google()
    // mavenCentral 快照仓库
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/google")
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
