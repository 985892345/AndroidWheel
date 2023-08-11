# AndroidWheel

Android 的各种扩展，提供依赖用于快速搭建项目

大部分来之掌邮

![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.985892345/extensions-android?server=https://s01.oss.sonatype.org&label=AndroidWheel)  

## 引入教程
目前还处于测试阶段，未发布稳定包，请先设置 MavenCentral 快照仓库后进行依赖
```kotlin
// setting.gradle.kts
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    // ...
    // mavenCentral 快照仓库
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
  }
}
```

## extensions 包

```kotlin
dependencies {
  implementation("io.github.985892345:extensions-android:x.y.z") // android 相关扩展
  implementation("io.github.985892345:extensions-gson:x.y.z") // gson 相关扩展
  implementation("io.github.985892345:extensions-rxjava:x.y.z") // rxjava 相关扩展
  implementation("io.github.985892345:extensions-rxpermissions:x.y.z") // rxpermissions 相关扩展
}
```

## utils 包

```kotlin
dependencies {
  implementation("io.github.985892345:utils-context:x.y.z") // 无侵入式全局 context
  implementation("io.github.985892345:utils-SimpleListAdapter:x.y.z") // ListAdpter 封装
}
```

