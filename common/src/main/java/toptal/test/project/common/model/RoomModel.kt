package toptal.test.project.common.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomModel(
    val id: String,
    val name: String,
    val co2: Int?,
    val humidity: Int?,
    val pm1: Int?,
    val pm10: Int?,
    val pm2_5: Int?,
    val temperature: Int?,
    val tvoc: Int?,
    val pressureDiff: Int?
)
