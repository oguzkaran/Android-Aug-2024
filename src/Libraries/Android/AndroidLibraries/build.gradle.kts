// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    id("androidx.room") version "2.7.1" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}