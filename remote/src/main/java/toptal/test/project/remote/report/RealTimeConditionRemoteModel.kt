package toptal.test.project.remote.report

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RealTimeConditionRemoteModel(
    val name: Map<String, String>,
    val timestamp: Long,
    val co2: Condition?,
    val humidity: Condition?,
    val pm1: Condition?,
    val pm10: Condition?,
    val pm2_5: Condition?,
    val temperature: Condition?,
    val tvoc: Condition?,
    val pressureDiff: Condition?
)

@JsonClass(generateAdapter = true)
data class Condition(val value: Float, val unit: String)
