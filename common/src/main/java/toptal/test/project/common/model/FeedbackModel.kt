package toptal.test.project.common.model

enum class Rating(val stringValue: String? = null, val point: Int? = null) {
    TOO_BAD("Too bad", 1), BAD("Bad", 2), OK("OK", 3), GOOD("Good", 4), VERY_GOOD("Very Good", 5), UNKNOWN;

    override fun toString(): String {
        return stringValue ?: super.toString()
    }
}

fun String.toRating(): Rating {
    Rating.values().forEach {
        if (it.toString() == this)
            return it
    }
    return Rating.UNKNOWN
}

data class FeedbackModel(
    val rating: Rating,
    val time: Long,
    val temperature: Int?,
    val freshness: Int?,
    val humidity: Int?,
    val smell: Float?,
    val cleanliness: Float?,
    val lighting: Int?,
    val sound: Int?,
    val workingAbility: Int?
)
