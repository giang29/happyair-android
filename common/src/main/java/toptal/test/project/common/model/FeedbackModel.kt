package toptal.test.project.common.model

import com.squareup.moshi.JsonClass

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

@JsonClass(generateAdapter = true)
data class ValueModel(
    val value: Float,
    val answer: Map<String, String>?
)

data class FeedbackModel(
    val rating: Rating,
    val time: Long,
    val temperature: ValueModel?,
    val freshness: ValueModel?,
    val humidity: ValueModel?,
    val smell: ValueModel?,
    val cleanliness: ValueModel?,
    val lighting: ValueModel?,
    val sound: ValueModel?,
    val workingAbility: ValueModel?
)
