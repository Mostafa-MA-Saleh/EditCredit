plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "mostafa.ma.saleh.gmail.com.editcreditdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "saleh.ma.mostafa.gmail.com.editcreditdemo"
        minSdk = 14
        targetSdk = 34
        versionCode = 17
        versionName = "3.0.3"
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":editcredit"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.10")
}