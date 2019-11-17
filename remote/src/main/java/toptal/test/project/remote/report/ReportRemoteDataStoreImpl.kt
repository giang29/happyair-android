package toptal.test.project.remote.report

import io.reactivex.Single
import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.common.model.ReportListModel
import toptal.test.project.common.model.ReportModel
import toptal.test.project.data.report.ReportRemoteDataStore
import toptal.test.project.remote.HappyAirGateway
import java.lang.IllegalArgumentException
import java.util.*

internal class ReportRemoteDataStoreImpl(
    private val happyAirGateway: HappyAirGateway
): ReportRemoteDataStore {
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

        return happyAirGateway.getReports(
            room,
            dataType.value,
            startTimeMillis / 1000,
            endTimeMillis / 1000,
            groupBy.value
        ).map {
            ReportListModel(
                it.room,
                AirDataType.toAirDataType(it.type),
                it.unit,
                groupBy,
                it.data.map { r ->
                    ReportModel(
                        Calendar.getInstance().apply {
                            timeInMillis = r.collected_time * 1000
                        },
                        r.value,
                        r.rating
                    )
                }
            )
        }
    }

    private fun getNormalizedTime(time: Calendar, groupBy: GroupType): Calendar {
        return when (groupBy) {
            GroupType.DAILY -> (time.clone() as Calendar).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                timeInMillis += timeZone.rawOffset
            }
            GroupType.MONTHLY -> (time.clone() as Calendar).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                set(Calendar.DAY_OF_MONTH, 1)
                timeInMillis += timeZone.rawOffset
            }
            GroupType.WEEKLY -> (time.clone() as Calendar).apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                timeInMillis += timeZone.rawOffset
            }
            GroupType.UNKNOWN -> throw IllegalArgumentException()
        }
    }
}
