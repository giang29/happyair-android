package toptal.test.project.remote.report

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportRemoteModel(
    val collected_time: String,
    val value: Float,
    val rating: Float?
)
