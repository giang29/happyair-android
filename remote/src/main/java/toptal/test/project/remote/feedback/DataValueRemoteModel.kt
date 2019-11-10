package toptal.test.project.remote.feedback

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataValueRemoteModel(
    val Timestamp: String,
    val Value: Float,
    val DataPointID: Int
)
