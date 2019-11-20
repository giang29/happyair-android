package toptal.test.project.remote.report

import io.reactivex.Single
import toptal.test.project.common.model.*
import toptal.test.project.common.model.Condition
import toptal.test.project.data.report.ReportRemoteDataStore
import toptal.test.project.remote.HappyAirGateway
import java.lang.IllegalArgumentException
import java.util.*

internal class ReportRemoteDataStoreImpl(
    private val happyAirGateway: HappyAirGateway
) : ReportRemoteDataStore {
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

    override fun loadRealtimeReport(): Single<List<RealTimeConditionModel>> {
        return happyAirGateway.getRealtimeCondition()
            .map {
                it.map { item ->
                    val conditions = listOfNotNull(
                        item.co2?.let { c ->
                            Condition(AirDataType.CO2, c.value, c.unit)
                        },
                        item.temperature?.let { c ->
                            Condition(AirDataType.TEMPERATURE, c.value, c.unit)
                        },
                        item.humidity?.let { c ->
                            Condition(AirDataType.HUMIDITY, c.value, c.unit)
                        },
                        item.tvoc?.let { c ->
                            Condition(AirDataType.TVOC, c.value, c.unit)
                        },
                        item.pm10?.let { c ->
                            Condition(AirDataType.PM10, c.value, c.unit)
                        },
                        item.pm2_5?.let { c ->
                            Condition(AirDataType.PM2_5, c.value, c.unit)
                        },
                        item.pm1?.let { c ->
                            Condition(AirDataType.PM1, c.value, c.unit)
                        },
                        item.pressureDiff?.let { c ->
                            Condition(AirDataType.PRESSURE_DIFF, c.value, c.unit)
                        }
                    )

                    val goodRatings = setOf(Rating.VERY_GOOD, Rating.GOOD)
                    val good = conditions.filter { goodRatings.contains(it.getRating()) }
                    val bad = conditions.filter { !goodRatings.contains(it.getRating()) }
                    RealTimeConditionModel(
                        item.name,
                        item.timestamp,
                        good,
                        bad
                    )
                }
            }
    }
}
