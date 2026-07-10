import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    kotlin("plugin.serialization") version(libs.versions.kotlin)
}

val apiKeyFile: File = rootProject.file("apiKey.properties")
val properties = Properties()
properties.load(FileInputStream(apiKeyFile))

android {
    namespace = "dev.brunofelix.movies"
    compileSdk = 37

    defaultConfig {
        applicationId = "dev.brunofelix.movies"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "dev.brunofelix.movies.test_util.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_KEY", properties["API_KEY"].toString())
        buildConfigField("String", "BASE_URL", properties["BASE_URL"].toString())
        buildConfigField("String", "BASE_URL_IMAGE", properties["BASE_URL_IMAGE"].toString())
        buildConfigField("String", "LANGUAGE", properties["LANGUAGE"].toString())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(17)
}

// Configuration to share dependencies between Unit and Instrumentation tests
val testCommon by configurations.creating
configurations.testImplementation.get().extendsFrom(testCommon)
configurations.androidTestImplementation.get().extendsFrom(testCommon)

dependencies {
    // AndroidX & Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.accompanist.flowlayout)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    // DI (Hilt)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.jetbrains.kotlin.metadata.jvm)

    // Networking & Serialization
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)

    // Coroutines & RxJava
    implementation(libs.jetbrains.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // Storage & Data
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.datastore.preferences)

    // Third Party Utilities
    implementation(libs.coil.compose)
    implementation(libs.timber)

    // --- Testing ---

    // Shared Test Dependencies
    testCommon(libs.truth)
    testCommon(libs.mockito.core)
    testCommon(libs.hilt.android.testing)
    testCommon(libs.kotlinx.coroutines.test)
    testCommon(libs.arch.core.testing)
    
    // Unit Tests Only
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.kotest)

    // Instrumentation Tests Only (Android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)

    // Debug Tools
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
