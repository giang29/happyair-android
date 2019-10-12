import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins { kotlin("jvm") }

val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions.jvmTarget = "1.8"

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
