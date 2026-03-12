plugins {
	alias(libs.plugins.kotlin.jvm)
	alias(libs.plugins.kotlin.spring)
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.spring.dependency.management)
}

group = "com.company"
version = "0.0.1-SNAPSHOT"
description = "The service registry project"

repositories {
	mavenCentral()
}

dependencies {
	implementation(libs.spring.boot.starter.webmvc)
	implementation(libs.kotlin.reflect)
	implementation(libs.spring.cloud.eureka.server)
	implementation(libs.jackson.module.kotlin)
	testImplementation(libs.spring.boot.starter.webmvc.test)
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
