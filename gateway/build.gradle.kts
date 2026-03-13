plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "com.company"
version = "0.0.1-SNAPSHOT"
description = "The gateway service"

dependencies {
    implementation(libs.spring.boot.starter.opentelemetry)
    implementation(libs.kotlin.reflect)
    implementation(libs.spring.cloud.gateway.server.webmvc)
    implementation(libs.spring.cloud.eureka.client)
    implementation(libs.springdoc.openapi.webmvc.ui)
    testImplementation(libs.spring.boot.starter.opentelemetry.test)
    testImplementation(libs.kotlin.test.junit5)
    testRuntimeOnly(libs.junit.platform.launcher)
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${libs.versions.spring.cloud.get()}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}
