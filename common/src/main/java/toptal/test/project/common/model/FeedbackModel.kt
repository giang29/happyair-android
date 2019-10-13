package toptal.test.project.common.model

enum class Rating(private val stringValue: String? = null) {
    TOO_BAD("Too bad"), BAD("Bad"), OK, GOOD("Good"), VERY_GOOD("Very Good"), UNKNOWN;

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
