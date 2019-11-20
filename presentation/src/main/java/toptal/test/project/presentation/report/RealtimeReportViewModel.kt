package toptal.test.project.presentation.report

import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import toptal.test.project.common.model.RealTimeConditionModel
import toptal.test.project.domain.report.FetchRealtimeReportUseCase
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState

data class RealtimeReportViewState(
    val reports: List<RealTimeConditionModel>
) : BaseViewState

class RealtimeReportViewModel(
    useCase: FetchRealtimeReportUseCase
) : BaseViewModel<RealtimeReportViewState>() {

    init {
        disposables += useCase.execute()
            .subscribeBy(
                onSuccess = {
                    _viewStates.value = RealtimeReportViewState(
                        it
                    )
                },
                onError = {}
            )
    }
}
