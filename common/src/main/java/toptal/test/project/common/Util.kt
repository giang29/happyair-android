package toptal.test.project.common

fun String.toDisplayDate(): String {
    return split("/").reversed().joinToString("/")
}
