package toptal.test.project.common.model

enum class GroupType {
    DAILY, MONTHLY, WEEKLY, UNKNOWN
}

enum class AirDataType {
    CO2, TEMPERATURE, UNKNOWN
}

data class ReportListModel(
    val room: String,
    val type: AirDataType,
    val unit: String,
    val groupBy: GroupType,
    val data: List<ReportModel>
)
