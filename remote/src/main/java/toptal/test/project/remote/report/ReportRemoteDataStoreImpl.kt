package toptal.test.project.remote.report

import io.reactivex.Single
import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.common.model.ReportListModel
import toptal.test.project.data.report.ReportRemoteDataStore
import java.lang.IllegalArgumentException
import java.util.*

internal class ReportRemoteDataStoreImpl: ReportRemoteDataStore {
    override fun loadReports(
        room: String,
        dataType: AirDataType,
        startTime: Calendar,
        endTime: Calendar,
        groupBy: GroupType
    ): Single<ReportListModel> {

        val normalizedEndtime = getNormalizedTime(endTime, groupBy)
        val normalizedStarttime = getNormalizedTime(startTime, groupBy)

        var endTimeMillis = normalizedEndtime.timeInMillis
        val startTimeMillis = normalizedStarttime.timeInMillis

        if (endTimeMillis <= startTimeMillis) {
            endTimeMillis = startTimeMillis + 30L * 24 * 60 * 60 * 1000
        }

        return Single.just(
            getMockReportData(room, dataType, normalizedStarttime, Calendar.getInstance().apply { timeInMillis = endTimeMillis }, groupBy)
        )
    }

    private fun getNormalizedTime(time: Calendar, groupBy: GroupType): Calendar {
        return when (groupBy) {
            GroupType.DAILY -> time.apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            GroupType.MONTHLY -> time.apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                set(Calendar.DAY_OF_MONTH, 1)
            }
            GroupType.WEEKLY -> time.apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            }
            GroupType.UNKNOWN -> throw IllegalArgumentException()
        }
    }
}
