package toptal.test.project.remote.feedback

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatingRemoteModel(
    val rating: Float,
    val timestamp: Long,
    val temperature: Int?,
    val freshness: Int?,
    val humidity: Int?,
    val smell: Float?,
    val cleanliness: Float?,
    val lighting: Int?,
    val sound: Int?,
    val workingAbility: Int?
)
