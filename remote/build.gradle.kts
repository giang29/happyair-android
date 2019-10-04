plugins {
    kotlin("jvm")
    id("kotlin-kapt")
}

dependencies {
    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":exceptions"))
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.retrofit) {
        exclude(module = "okhttp")
    }
    implementation(Dependencies.retrofitConverterMoshi)
    implementation(Dependencies.retrofitAdapterRxJava2)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggingInterceptor)
    implementation(Dependencies.moshi)
    implementation(Dependencies.rxKotlin)
    kapt(Dependencies.moshiCodeGen)
    implementation(Dependencies.kodein)
}

java {
    sourceCompatibility = AndroidSettings.sourceCompatibility
    targetCompatibility = AndroidSettings.targetCompatibility
}
repositories {
    mavenCentral()
}
