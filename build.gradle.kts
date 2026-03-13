plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.kotlin.jpa) apply false
    alias(libs.plugins.spring.boot) apply false
    alias(libs.plugins.spring.dependency.management) apply false
}

subprojects {
    afterEvaluate {
        extensions.findByType<JavaPluginExtension>()?.toolchain {
            languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
        }

        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }
}
