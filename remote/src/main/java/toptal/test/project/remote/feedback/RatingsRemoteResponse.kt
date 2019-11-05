package toptal.test.project.remote.feedback

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatingsRemoteResponse(
    val ratings: List<RatingRemoteModel>
)
