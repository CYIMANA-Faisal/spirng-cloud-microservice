pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "spring-cloud-microservice"
include("shared-lib")
include("security-lib")
include("observability-lib")
include("gateway")
include("iam")
include("discovery")
