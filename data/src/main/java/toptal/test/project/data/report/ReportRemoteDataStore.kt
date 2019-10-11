package toptal.test.project.data.report

import io.reactivex.Single
import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.common.model.ReportListModel
import java.util.*

interface ReportRemoteDataStore {
    fun loadReports(
        room: String,
        dataType: AirDataType,
        startTime: Calendar = Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000 },
        endTime: Calendar = Calendar.getInstance(),
        groupBy: GroupType = GroupType.DAILY
    ): Single<ReportListModel>
}
