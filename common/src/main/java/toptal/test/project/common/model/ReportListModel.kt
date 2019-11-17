package toptal.test.project.common.model

enum class GroupType(val value: String) {
    DAILY("day"), MONTHLY("month"), WEEKLY("week"), UNKNOWN("unknown")
}

enum class AirDataType(val value: String) {
    CO2("co2"),
    TEMPERATURE("temperature"),
    HUMIDITY("humidity"),
    PM1("pm1"),
    PM10("pm10"),
    PM2_5("pm2_5"),
    TVOC("tvoc"),
    PRESSURE_DIFF("pressureDiff"),
    UNKNOWN("unknown");

    companion object {
        fun toAirDataType(s: String): AirDataType {
            AirDataType.values().forEach {
                if (it.value == s) {
                    return it
                }
            }
            return UNKNOWN
        }
    }
}

data class ReportListModel(
    val room: String,
    val type: AirDataType,
    val unit: String,
    val groupBy: GroupType?,
    val data: List<ReportModel>
)
