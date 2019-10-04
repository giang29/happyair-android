plugins { kotlin("jvm") }

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(project(":exceptions"))

    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.rxJava)
    implementation(Dependencies.rxKotlin)
    implementation(Dependencies.kodein)
    implementation(Dependencies.rxReplayingShare)
    implementation(Dependencies.rxRelay)
    implementation(Dependencies.koptional)
}

java {
    sourceCompatibility = AndroidSettings.sourceCompatibility
    targetCompatibility = AndroidSettings.targetCompatibility
}
