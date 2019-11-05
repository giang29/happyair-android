package toptal.test.project.common.model

enum class Rating(private val stringValue: String? = null, val point: Int? = null) {
    TOO_BAD("Too bad", 1), BAD("Bad", 2), OK("OK", 3), GOOD("Good", 4), VERY_GOOD("Very Good", 5), UNKNOWN;

    override fun toString(): String {
        return stringValue ?: super.toString()
    }

    fun String.toRating(): Rating {
        values().forEach {
            if (it.toString() == this)
                return it
        }
        return UNKNOWN
    }
}

data class FeedbackModel(
    val rating: Rating,
    val time: Long
)
