package toptal.test.project.presentation.report

import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.common.model.ReportListModel
import toptal.test.project.domain.report.FetchReportForRoomUseCase
import toptal.test.project.domain.room.FetchRoomUseCase
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState
import toptal.test.project.presentation.model.RoomPresentationModel
import java.util.*

sealed class ReportViewState: BaseViewState {
    data class Initial(val rooms: List<RoomPresentationModel>): ReportViewState()
    data class Success(val reportListModel: ReportListModel): ReportViewState()
    object Loading: ReportViewState()
    object Failure: ReportViewState()
}

class ReportViewModel(
    fetchRoomUseCase: FetchRoomUseCase,
    private val useCase: FetchReportForRoomUseCase
): BaseViewModel<ReportViewState>() {

    init {
        disposables += fetchRoomUseCase.execute()
            .subscribeBy(
                onSuccess = {
                    _viewStates.value = ReportViewState.Initial(
                        it.map { model ->
                            model.run {
                                RoomPresentationModel(
                                    id,
                                    name,
                                    co2,
                                    humidity,
                                    pm1,
                                    pm10,
                                    pm2_5,
                                    temperature,
                                    tvoc,
                                    pressureDiff
                                )
                            }
                        }
                    )
                },
                onError = {}
            )
    }

    fun loadReport(
        room: String,
        dataType: AirDataType,
        startTime: Calendar,
        endTime: Calendar,
        groupBy: GroupType
    ) {
        _viewStates.value = ReportViewState.Loading
        disposables += useCase.execute(
            room, dataType, startTime, endTime, groupBy
        ).subscribeBy(
            onError = {
                _viewStates.value = ReportViewState.Failure
            },
            onSuccess = {
                _viewStates.value = ReportViewState.Success(it)
            }
        )
    }
}
