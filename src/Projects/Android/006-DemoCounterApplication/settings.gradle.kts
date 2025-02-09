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
        maven {
            url = uri("https://raw.github.com/oguzkaran/android-aug-2024-maven-repo/main")
        }

        maven {
            url = uri("https://raw.github.com/oguzkaran/android-aug-2024-karadev-maven-repo/main")
        }
    }
}

rootProject.name = "006-DemoCounterApplication"
include(":app")
include(":CounterDataServiceLib")
