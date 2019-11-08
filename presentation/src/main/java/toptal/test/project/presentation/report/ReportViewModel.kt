package toptal.test.project.presentation.report

import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.common.model.ReportListModel
import toptal.test.project.domain.report.FetchReportForRoomUseCase
import toptal.test.project.domain.room.FetchRoomUseCase
import toptal.test.project.presentation.Event
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState
import toptal.test.project.presentation.model.RoomPresentationModel
import java.util.*

data class ReportViewState(
    val rooms: Event<List<RoomPresentationModel>>? = null,
    val reportListModel: Event<ReportListModel>? = null
) : BaseViewState

class ReportViewModel(
    fetchRoomUseCase: FetchRoomUseCase,
    private val useCase: FetchReportForRoomUseCase
) : BaseViewModel<ReportViewState>() {

    init {
        _viewStates.value = ReportViewState()
        disposables += fetchRoomUseCase.execute()
            .subscribeBy(
                onSuccess = {
                    _viewStates.value = viewStates.value?.copy(
                        rooms = Event(it.map { model ->
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
                        })
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
        disposables += useCase.execute(
            room, dataType, startTime, endTime, groupBy
        ).subscribeBy(
            onError = {

            },
            onSuccess = {
                _viewStates.value = viewStates.value?.copy(reportListModel = Event(it))
            }
        )
    }
}
