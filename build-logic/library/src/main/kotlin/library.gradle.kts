import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.dependencies

plugins {
  id("com.android.library")
  id("kotlin-android")
}

android {
  compileSdk = 34

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
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  kotlinOptions {
    jvmTarget = "11"
  }
}

kotlin {
  jvmToolchain(11)
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
