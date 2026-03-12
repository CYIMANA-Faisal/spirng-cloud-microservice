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
    // oauth2-resource-server transitively brings in spring-security-core and spring-security-oauth2-jose
    // which provide Jwt, GrantedAuthority, Converter, and related Spring Security types
    api(libs.spring.boot.starter.oauth2.resource.server)
    api(libs.kotlin.reflect)
}
