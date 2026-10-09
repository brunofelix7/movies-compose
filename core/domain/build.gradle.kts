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

    // Dependency injection (JSR-330 annotations only)
    implementation(libs.javax.inject)

    // Unit tests
    testImplementation(libs.bundles.unit.test)
}
