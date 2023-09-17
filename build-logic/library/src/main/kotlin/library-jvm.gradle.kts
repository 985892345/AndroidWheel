plugins {
  kotlin("jvm")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}


// 发布 maven 需要
val sourceSets = extensions.getByName("sourceSets") as SourceSetContainer
tasks.register("javadocJar", Jar::class.java) {
  archiveClassifier.set("javadoc")
  from("javadoc")
}

tasks.register("sourcesJar", Jar::class.java) {
  archiveClassifier.set("sources")
  from(sourceSets["main"].allSource)
}