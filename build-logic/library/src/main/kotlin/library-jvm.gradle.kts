plugins {
  kotlin("jvm")
  id("io.github.985892345.MavenPublisher")
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
  jvmToolchain(11)
}
