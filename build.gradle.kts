import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.

apply(plugin = "com.github.ben-manes.versions")
buildscript {
    val updatePluginVersion = "0.26.0"
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Dependencies.androidGradlePlugin)
        classpath(kotlin("gradle-plugin", version = Versions.kotlinStdLib))
        classpath(Dependencies.kotlinAndroidExtensions)
        classpath("com.github.ben-manes:gradle-versions-plugin:$updatePluginVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
        maven(url = "https://dl.bintray.com/kodein-framework/Kodein-DI")
    }
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir)
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

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
    resolutionStrategy {
        componentSelection {
            all {
                val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea")
                    .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
                    .any { it.matches(candidate.version) }
                if (rejected) {
                    reject("Release candidate")
                }
            }
        }
    }
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}
