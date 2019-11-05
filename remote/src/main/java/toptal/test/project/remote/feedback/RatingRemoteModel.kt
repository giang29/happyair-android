package toptal.test.project.remote.feedback

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatingRemoteModel(
    val rating: Float,
    val timestamp: Long
)
