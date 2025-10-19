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
        mavenLocal()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        mavenLocal()
        maven {
            url = uri("https://raw.github.com/oguzkaran/android-aug-2024-maven-repo/main")
        }

    }
}

rootProject.name = "AndroidLibraries"
include(":DateTimeUtilLib")
include(":MapUtilLib")
include(":FileObserverLib")
include(":IntentUtilLib")
