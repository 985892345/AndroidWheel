import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.dependencies

plugins {
  id("com.android.library")
  id("kotlin-android")
}

android {
  compileSdk = 33

  defaultConfig {
    minSdk = 24

    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation("androidx.appcompat:appcompat:1.6.1")
}


// 发布 maven 需要
android {
  publishing {
    singleVariant("release") {
      withJavadocJar()
      withSourcesJar()
    }
  }
}
