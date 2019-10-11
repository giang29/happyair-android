package toptal.test.project.domain.report

import io.reactivex.Scheduler
import io.reactivex.Single
import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.common.model.ReportListModel
import java.util.*

interface FetchReportForRoomUseCase {
    fun execute(
        room: String,
        dataType: AirDataType,
        startTime: Calendar,
        endTime: Calendar,
        groupBy: GroupType
    ) : Single<ReportListModel>
}

internal class FetchReportForRoomUseCaseImpl(
    private val reportRepository: ReportRepository,
    private val executionThread: Scheduler,
    private val postExecutionThread: Scheduler
): FetchReportForRoomUseCase {
    override fun execute(
        room: String,
        dataType: AirDataType,
        startTime: Calendar,
        endTime: Calendar,
        groupBy: GroupType
    ): Single<ReportListModel> {
        return reportRepository.loadReports(room, dataType, startTime, endTime, groupBy)
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }
}
