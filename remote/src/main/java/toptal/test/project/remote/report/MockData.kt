package toptal.test.project.remote.report

import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.common.model.ReportListModel
import toptal.test.project.common.model.ReportModel
import java.lang.IllegalArgumentException
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.Calendar.DATE
import java.util.Calendar.MONTH
import kotlin.random.Random

internal fun getMockReportData(
    room: String,
    dataType: AirDataType,
    startTime: Calendar,
    endTime: Calendar,
    groupBy: GroupType
): ReportListModel {

    val reports = mutableListOf<ReportModel>()

    while (reports.isEmpty() || reports.last().collectedTime <= endTime) {
        val time = (reports.lastOrNull()?.collectedTime?.clone() ?: startTime.clone()) as Calendar
        when (groupBy) {
            GroupType.DAILY -> time.add(DATE, 1)
            GroupType.MONTHLY -> time.add(MONTH, 1)
            GroupType.WEEKLY -> time.add(DATE, 7)
            GroupType.UNKNOWN -> throw IllegalArgumentException()
        }
        val rating = Random.nextFloat() * 4 + 1
        val value = when (dataType) {
            AirDataType.CO2 -> Random.nextFloat() * 200 + 100
            AirDataType.TEMPERATURE -> Random.nextFloat() * 10 + 20
            AirDataType.UNKNOWN -> throw IllegalArgumentException()
        }
        reports.add(ReportModel(time, value, rating))
    }

    reports.removeAt(reports.size - 1)

    return ReportListModel(
        room,
        dataType,
        when (dataType) {
            AirDataType.CO2 -> "ppm"
            AirDataType.TEMPERATURE -> "°C"
            AirDataType.UNKNOWN -> throw IllegalArgumentException()
        },
        groupBy,
        reports
    )
}
