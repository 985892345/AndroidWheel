@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.kotlin.jvm)
  id("publish")
}
publish.artifactId = "jvm-generics"
publish.mavenPublicationBlock = {
  artifact(tasks["javadocJar"])
  artifact(tasks["sourcesJar"])
  from(components["java"])
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

val sourceSets = extensions.getByName("sourceSets") as SourceSetContainer

tasks.register("javadocJar", Jar::class.java) {
  archiveClassifier.set("javadoc")
  from("javadoc")
}

tasks.register("sourcesJar", Jar::class.java) {
  archiveClassifier.set("sources")
  from(sourceSets["main"].allSource)
}