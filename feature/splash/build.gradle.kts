plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.brunofelix.movies.feature.splash"
    compileSdk = 37

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
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
    implementation(project(":core:presentation"))
    implementation(project(":core:designsystem"))

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.ui.tooling)

    // Lifecycle / ViewModel
    implementation(libs.bundles.lifecycle)

    // Navigation
    implementation(libs.androidx.navigation3.runtime)

    // Unit tests
    testImplementation(libs.bundles.unit.test)

    // Instrumentation tests
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.ui.test.manifest)
}
