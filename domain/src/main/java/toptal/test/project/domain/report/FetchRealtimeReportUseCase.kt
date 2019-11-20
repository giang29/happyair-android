package toptal.test.project.domain.report

import io.reactivex.Scheduler
import io.reactivex.Single
import toptal.test.project.common.model.RealTimeConditionModel

interface FetchRealtimeReportUseCase {
    fun execute() : Single<List<RealTimeConditionModel>>
}

internal class FetchRealtimeReportUseCaseImpl(
    private val reportRepository: ReportRepository,
    private val executionThread: Scheduler,
    private val postExecutionThread: Scheduler
): FetchRealtimeReportUseCase {
    override fun execute(): Single<List<RealTimeConditionModel>> {
        return reportRepository.loadRealtimeReports()
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }
}
