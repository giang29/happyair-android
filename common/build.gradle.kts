plugins {
    kotlin("jvm")
    id("kotlin-kapt")
}

dependencies {
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.moshi)
    kapt(Dependencies.moshiCodeGen)
}

java {
    sourceCompatibility = AndroidSettings.sourceCompatibility
    targetCompatibility = AndroidSettings.targetCompatibility
}
