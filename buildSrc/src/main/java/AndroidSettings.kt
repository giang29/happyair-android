import org.gradle.api.JavaVersion

object AndroidSettings {
    const val compileSdkVersion = 28
    const val minSdkVersion = 21
    const val targetSdkVersion = 28
    val sourceCompatibility = JavaVersion.VERSION_1_8
    val targetCompatibility = JavaVersion.VERSION_1_8
    const val versionCode = 1
    const val versionName = "01.00.00"
}
