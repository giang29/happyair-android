package toptal.test.project.data.report

import io.reactivex.Single
import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.common.model.ReportListModel
import toptal.test.project.domain.report.ReportRepository
import java.util.*

internal class ReportRepositoryImpl(private val reportRemoteDataStore: ReportRemoteDataStore) :
    ReportRepository {
    override fun loadReports(
        room: String,
        dataType: AirDataType,
        startTime: Calendar,
        endTime: Calendar,
        groupBy: GroupType
    ): Single<ReportListModel> {
        return reportRemoteDataStore.loadReports(room, dataType, startTime, endTime, groupBy)
    }
}
