package toptal.test.project.remote.report

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportListRemoteModel(
    val room: String,
    val type: String,
    val unit: String,
    val group_by: String?,
    val data: List<ReportRemoteModel>
)
