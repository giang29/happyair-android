package toptal.test.project.domain.report

import io.reactivex.Single
import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.common.model.RealTimeConditionModel
import toptal.test.project.common.model.ReportListModel
import java.util.*

interface ReportRepository {
    fun loadReports(
        room: String,
        dataType: AirDataType,
        startTime: Calendar,
        endTime: Calendar,
        groupBy: GroupType
    ): Single<ReportListModel>

    fun loadRealtimeReports(): Single<List<RealTimeConditionModel>>
}
