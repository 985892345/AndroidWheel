# AndroidWheel

Android 的各种扩展，提供依赖用于快速搭建项目

大部分来自掌邮
![Maven Central](https://img.shields.io/maven-central/v/io.github.985892345/base-ui?server=https://s01.oss.sonatype.org&label=release)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.github.985892345/base-ui?server=https://s01.oss.sonatype.org&label=SNAPSHOT)

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

## base 包

```kotlin
dependencies {
  implementation("io.github.985892345:base-all:x.y.z") // 包含所有 base 包
}
dependencies {
  implementation("io.github.985892345:base-ui:x.y.z") // BaseActivity、BaseFragment、BaseViewModel 基类
  implementation("io.github.985892345:base-databinding:x.y.z") // BaseBindActivity、BaseBindFragment
}
```

## extensions 包

```kotlin
dependencies {
  implementation("io.github.985892345:extensions-all:x.y.z") // 包含所有 extensions 包
}
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
  implementation("io.github.985892345:utils-all:x.y.z") // 包含所有 utils 包
}
dependencies {
  implementation("io.github.985892345:utils-adapter:x.y.z") // Adapter 相关
  implementation("io.github.985892345:utils-context:x.y.z") // 无侵入式全局 context
  implementation("io.github.985892345:utils-impl:x.y.z") // 一些接口的默认实现
  implementation("io.github.985892345:utils-view:x.y.z") // View 相关
}
```

## jvm 包

```kotlin
dependencies {
  implementation("io.github.985892345:jvm-all:x.y.z") // 包含所有 jvm 包
}
dependencies {
  implementation("io.github.985892345:jvm-exception:x.y.z") // 异常处理
  implementation("io.github.985892345:jvm-flow:x.y.z") // flow 扩展
  implementation("io.github.985892345:jvm-generics:x.y.z") // 泛型工具
  implementation("io.github.985892345:jvm-impl:x.y.z") // by defaultImpl() 快速实现接口
  implementation("io.github.985892345:jvm-rxjava:x.y.z") // rxjava 扩展
}
```

