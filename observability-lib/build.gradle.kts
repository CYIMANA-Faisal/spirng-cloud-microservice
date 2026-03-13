plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    `java-library`
}

tasks.bootJar { enabled = false }
tasks.jar {
    enabled = true
    archiveClassifier.set("")
}

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

dependencies {
    // Web is implementation (not api) — no public API types exposed yet.
    // Swap to api once a filter/interceptor type is added that consumers reference directly.
    implementation(libs.spring.boot.starter.web)
    implementation(libs.kotlin.reflect)
}
