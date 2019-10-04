// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Dependencies.androidGradlePlugin)
        classpath(kotlin("gradle-plugin", version = Versions.kotlinStdLib))
        classpath(Dependencies.kotlinAndroidExtensions)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    configurations.all {
        resolutionStrategy {
            eachDependency {
                if (requested.group == "org.jetbrains.kotlin" && requested.name.startsWith("kotlin-stdlib")) {
                    useVersion(Versions.kotlinStdLib)
                }
                if (requested.group == "com.squareup.okhttp3") useVersion(Versions.okHttp)
                if (requested.group == "io.reactivex.rxjava2" && requested.name == "rxjava") {
                    useVersion(Versions.rxJava)
                }
            }
        }
    }
}
repositories {
    mavenCentral()
}
