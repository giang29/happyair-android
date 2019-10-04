plugins { kotlin("jvm") }

dependencies {
    implementation(Dependencies.kotlinStdLib)
    implementation(Dependencies.okHttp)
}

java {
    sourceCompatibility = AndroidSettings.sourceCompatibility
    targetCompatibility = AndroidSettings.targetCompatibility
}

