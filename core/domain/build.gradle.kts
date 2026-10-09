plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    // Coroutines
    implementation(libs.jetbrains.kotlinx.coroutines.core)

    // Unit tests
    testImplementation(libs.bundles.unit.test)
}
