import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("io.objectbox") // Add after other plugins
}

android {
    namespace = "org.csystem.android.data.objectbox"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 25

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.objectbox.android)
    kapt(libs.objectbox.processor)
    annotationProcessor(libs.objectbox.processor)
    androidTestImplementation(libs.objectbox.android)
    kaptAndroidTest(libs.objectbox.processor)
    androidTestAnnotationProcessor(libs.objectbox.processor)


    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler)
    kapt(libs.hilt.android.compiler)
}

kapt {
    correctErrorTypes = true
}