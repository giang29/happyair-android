plugins { kotlin("jvm") }

dependencies {
    implementation(project(":common"))
    implementation(project(":exceptions"))

    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.rxJava)
    implementation(Dependencies.rxKotlin)
    implementation(Dependencies.kodein)
    implementation(Dependencies.koptional)
}

java {
    sourceCompatibility = AndroidSettings.sourceCompatibility
    targetCompatibility = AndroidSettings.targetCompatibility
}
