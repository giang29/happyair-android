object Versions {
    // Android gradle plugin
    const val androidGradlePlugin = "3.5.0"

    // Kotlin stdlib
    const val kotlinStdLib = "1.3.11"

    // AndroidX
    const val androidX = "1.0.0"

    // Retrofit
    const val retrofit = "2.5.0"

    // OkHttp
    const val okHttp = "3.11.0"

    // Moshi
    const val moshi = "1.8.0"

    // Kodein
    const val kodein = "5.3.0"

    // Rx
    const val rxJava = "2.1.12"
    const val rxAndroid = "2.0.2"
    const val rxRelay = "2.0.0"
    const val rxBinding = "3.0.0-alpha1"
    const val rxKotlin = "2.2.0"
    const val rxReplayingShare = "2.0.1"

    // ConstraintLayout
    const val constraintLayout = "1.1.3"

    // Android Architecture Components
    const val lifecycle = "2.0.0"

    // koptional
    const val koptional = "1.3.0"

    // Android KTX
    const val androidKtxCore = "1.0.0-beta01"

    // Groupie
    const val groupie = "2.5.1"

    const val anyChart = "1.1.2"
}

object Dependencies {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val anyChart = "com.github.AnyChart:AnyChart-Android:${Versions.anyChart}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinStdLib}"
    const val kotlinAndroidExtensions =
        "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlinStdLib}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidX}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitAdapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val kodein = "org.kodein.di:kodein-di-generic-jvm:${Versions.kodein}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val livecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val rxRelay = "com.jakewharton.rxrelay2:rxrelay:${Versions.rxRelay}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.androidX}"
    const val design = "com.google.android.material:material:${Versions.androidX}"
    const val rxBindingPlatform =
        "com.jakewharton.rxbinding3:rxbinding:${Versions.rxBinding}"
    const val rxBindingAppCompat =
        "com.jakewharton.rxbinding3:rxbinding-appcompat:${Versions.rxBinding}"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"
    const val koptional = "com.gojuno.koptional:koptional:${Versions.koptional}"
    const val cardView = "androidx.cardview:cardview:${Versions.androidX}"
    const val androidKtx = "androidx.core:core-ktx:${Versions.androidKtxCore}"
    const val groupie = "com.xwray:groupie:${Versions.groupie}"
    const val groupieKotlinAndroidExtensions =
        "com.xwray:groupie-kotlin-android-extensions:${Versions.groupie}"
    const val rxReplayingShare =
        "com.jakewharton.rx2:replaying-share-kotlin:${Versions.rxReplayingShare}"
}
