import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(AndroidSettings.compileSdkVersion)
    defaultConfig {
        minSdkVersion(AndroidSettings.minSdkVersion)
        targetSdkVersion(AndroidSettings.targetSdkVersion)
    }
    compileOptions {
        sourceCompatibility = AndroidSettings.sourceCompatibility
        targetCompatibility = AndroidSettings.targetCompatibility
    }
}

// IMPORTANT!  Enables view caching in viewholders.
// See: https://github.com/Kotlin/KEEP/blob/master/proposals/android-extensions-entity-caching.md
androidExtensions {
    configure(delegateClosureOf<AndroidExtensionsExtension> {
        isExperimental = true
    })
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":exceptions"))

    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.kodein)
    implementation(Dependencies.koptional)
    implementation(Dependencies.livecycleExtensions)
    kapt(Dependencies.lifecycleCompiler)
    implementation(Dependencies.rxJava)
    implementation(Dependencies.rxKotlin)
    implementation(Dependencies.rxRelay)
}
