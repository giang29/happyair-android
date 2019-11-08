package toptal.test.project.presentation.report

import com.jakewharton.rxrelay2.PublishRelay
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
import java.util.concurrent.TimeUnit

data class ReportViewState(
    val rooms: Event<List<RoomPresentationModel>>? = null,
    val reportListModel: Event<ReportListModel>? = null
) : BaseViewState

data class Filter(
    val room: String,
    val dataType: AirDataType,
    val startTime: Calendar,
    val endTime: Calendar,
    val groupBy: GroupType
)

class ReportViewModel(
    fetchRoomUseCase: FetchRoomUseCase,
    private val useCase: FetchReportForRoomUseCase
) : BaseViewModel<ReportViewState>() {

    private val relay = PublishRelay.create<Filter>()

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
        disposables += relay.throttleFirst(100, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                useCase.execute(
                    it.room, it.dataType, it.startTime, it.endTime, it.groupBy
                ).toObservable()
            }.subscribeBy(
                onError = {

                },
                onNext = {
                    _viewStates.value = viewStates.value?.copy(reportListModel = Event(it))
                }
            )
    }

    fun loadReport(
        room: String,
        dataType: AirDataType,
        startTime: Calendar,
        endTime: Calendar,
        groupBy: GroupType
    ) {
        relay.accept(
            Filter(room, dataType, startTime, endTime, groupBy)
        )
    }
}
