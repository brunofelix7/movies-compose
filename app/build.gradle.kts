import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

// Signing credentials, kept out of version control. Absent on machines that only build debug,
// in which case the release build stays unsigned instead of failing to configure.
val keystoreFile: File = rootProject.file("keystore.properties")
val keystoreProperties = Properties().apply {
    if (keystoreFile.exists()) {
        FileInputStream(keystoreFile).use { load(it) }
    }
}

android {
    namespace = "dev.brunofelix.movies"
    compileSdk = 37

    defaultConfig {
        applicationId = "dev.brunofelix.movies"
        minSdk = 26
        targetSdk = 36
        versionCode = 7
        versionName = "1.0.1"
        testInstrumentationRunner = "dev.brunofelix.movies.test_util.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            if (keystoreFile.exists()) {
                storeFile = file(keystoreProperties.getProperty("STORE_FILE"))
                storePassword = keystoreProperties.getProperty("STORE_PASSWORD")
                keyAlias = keystoreProperties.getProperty("KEY_ALIAS")
                keyPassword = keystoreProperties.getProperty("KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            if (keystoreFile.exists()) {
                signingConfig = signingConfigs.getByName("release")
            }
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "version"
    productFlavors {
        create("prod") {
            dimension = "version"
        }
        create("beta") {
            dimension = "version"
            applicationIdSuffix = ".beta"
            versionNameSuffix = "-beta"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    // Modules
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:presentation"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:movie"))
    implementation(project(":feature:tv_show"))
    implementation(project(":feature:favorite"))
    implementation(project(":feature:release"))
    implementation(project(":feature:search"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:media_list"))

    // AndroidX & Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.splashscreen)

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.ui.tooling)

    // Lifecycle
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.lifecycle.viewmodel.compose)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.jetbrains.kotlin.metadata.jvm)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Logging
    implementation(libs.timber)

    // Unit tests
    testImplementation(libs.bundles.unit.test)

    // Instrumentation tests
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)
    kspAndroidTest(libs.jetbrains.kotlin.metadata.jvm)
    debugImplementation(libs.ui.test.manifest)
}
