package toptal.test.project.common.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomModel(
    val id: String,
    val name: String
)
