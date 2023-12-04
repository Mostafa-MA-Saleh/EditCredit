plugins {
    id("com.android.library")
    id("maven-publish")
    kotlin("android")
}

android {
    namespace = "mostafa.ma.saleh.gmail.com.editcredit"
    compileSdk  = 34

    defaultConfig {
        minSdk = 14
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            val defaultProguardFile = getDefaultProguardFile("proguard-android-optimize.txt")
            proguardFiles(defaultProguardFile, "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.10")
}

afterEvaluate {
    publishing {
        publications {
            register("release", MavenPublication::class) {
                from(components["release"])
                groupId = "com.github.Mostafa-MA-Saleh"
                artifactId = "EditCredit"
                version = "3.0.3"
            }
        }
    }
}