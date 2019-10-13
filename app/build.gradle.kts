import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(AndroidSettings.compileSdkVersion)
    defaultConfig {
        applicationId = "toptal.test.project.meal"
        minSdkVersion(AndroidSettings.minSdkVersion)
        targetSdkVersion(AndroidSettings.targetSdkVersion)
        versionCode = AndroidSettings.versionCode
        versionName = AndroidSettings.versionName
        vectorDrawables.useSupportLibrary = true
    }
    compileOptions {
        sourceCompatibility = AndroidSettings.sourceCompatibility
        targetCompatibility = AndroidSettings.targetCompatibility
    }

    kotlinOptions {
        this as KotlinJvmOptions
        jvmTarget = "1.8"
    }
}

// IMPORTANT!  Enables view caching in viewholders.
// See: https://github.com/Kotlin/KEEP/blob/master/proposals/android-extensions-entity-caching.md
androidExtensions {
    fun AndroidExtensionsExtension.configure() {
        isExperimental = true
    }
    configure(AndroidExtensionsExtension::configure)
}

dependencies {
    implementation(project(":common"))
    implementation(project(":exceptions"))
    implementation(project(":injection"))
    implementation(project(":presentation"))
    implementation(Dependencies.anyChart)
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.kodein)
    implementation(Dependencies.rxAndroid)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.livecycleExtensions)
    kapt(Dependencies.lifecycleCompiler)
    implementation(Dependencies.recyclerView)
    implementation(Dependencies.design)
    implementation(Dependencies.rxBindingPlatform)
    implementation(Dependencies.rxBindingAppCompat)
    implementation(Dependencies.rxKotlin)
    implementation(Dependencies.rxRelay)
    implementation(Dependencies.androidKtx)
    implementation(Dependencies.cardView)
    implementation(Dependencies.groupie)
    implementation(Dependencies.groupieKotlinAndroidExtensions)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUi)
}
