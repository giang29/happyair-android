plugins {
    id("com.android.library")
    id("kotlin-android")
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

dependencies {

    implementation(project(":data"))
    implementation(project(":common"))
    implementation(project(":exceptions"))

    implementation(Dependencies.kodein)
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.rxKotlin)
    implementation(Dependencies.rxAndroid)
}
