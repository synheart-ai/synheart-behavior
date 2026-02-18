pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SynheartBehaviorExample"
include(":app")

// Composite build: resolve ai.synheart:synheart-behavior from the local SDK repo
includeBuild("../../../synheart-behavior-kotlin") {
    dependencySubstitution {
        substitute(module("ai.synheart:synheart-behavior")).using(project(":"))
    }
}
