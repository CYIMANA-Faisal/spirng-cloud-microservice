plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
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
    // Exposed as api because consumers reference these types directly
    // (controllers return ApiResponse, services throw the exceptions, entities extend BaseEntity)
    api(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.security)
    api(libs.spring.boot.starter.data.jpa)
    api(libs.spring.boot.starter.validation)
    api(libs.jackson.module.kotlin)
    api(libs.kotlin.reflect)
}
