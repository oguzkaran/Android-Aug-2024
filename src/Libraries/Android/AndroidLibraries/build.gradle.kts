// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    id("androidx.room") version "2.8.2" apply false
    id("com.google.devtools.ksp") version "2.2.20-2.0.3" apply false
}