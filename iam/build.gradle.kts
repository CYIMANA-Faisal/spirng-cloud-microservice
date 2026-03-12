plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}
group = "com.company"
version = "0.0.1-SNAPSHOT"
description = "The Identity Access Management Service"

repositories {
	mavenCentral()
}

dependencies {
	implementation(project(":shared-lib"))
	implementation(libs.spring.boot.starter.web)
	implementation(libs.spring.boot.starter.oauth2.resource.server)
	implementation(libs.kotlin.reflect)
	implementation(libs.spring.cloud.eureka.client)
	implementation(libs.springdoc.openapi.webmvc.api)
	implementation(libs.spring.cloud.circuitbreaker.resilience4j)
	implementation(libs.keycloak.admin.client)
	testImplementation(libs.spring.boot.starter.test)
	testImplementation(libs.spring.security.test)
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
