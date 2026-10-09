import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val apiKeyFile: File = rootProject.file("apiKey.properties")
val apiProperties = Properties().apply {
    FileInputStream(apiKeyFile).use { load(it) }
}

android {
    namespace = "dev.brunofelix.movies.core.data"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", apiProperties["API_KEY"].toString())
        buildConfigField("String", "BASE_URL", apiProperties["BASE_URL"].toString())
        buildConfigField("String", "BASE_URL_IMAGE", apiProperties["BASE_URL_IMAGE"].toString())
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        buildConfig = true
    }

    testOptions {
        unitTests.all { it.useJUnitPlatform() }
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // Modules
    implementation(project(":core:domain"))

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Retrofit / OkHttp
    implementation(libs.bundles.networking)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.jetbrains.kotlin.metadata.jvm)

    // Unit tests
    testImplementation(libs.bundles.unit.test)
    testImplementation(libs.mockwebserver)

    // Instrumentation tests
    androidTestImplementation(libs.bundles.android.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.turbine)
}
