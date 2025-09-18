pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Plated"
include(":app")
include(":core-model")
include(":core-network")
include(":core-ui")
include(":core-utils")
include(":feature-auth")
include(":feature-home")
include(":feature-profile")
include(":feature-recipe")
include(":core-navigation")
include(":core-resources")
