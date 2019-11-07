package toptal.test.project.presentation.report

import android.os.Parcelable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.parcel.Parcelize
import toptal.test.project.common.model.Rating
import toptal.test.project.domain.feedback.FetchAllFeedbackUseCase
import toptal.test.project.domain.room.FetchRoomUseCase
import toptal.test.project.presentation.Event
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState
import toptal.test.project.presentation.model.RoomPresentationModel

data class FeedbackViewState(
    val rooms: Event<List<RoomPresentationModel>>? = null,
    val loadingFeedback: Boolean = false,
    val error: Event<Throwable>? = null,
    val feedbacks: Event<List<FeedbackPresentationModel>>? = null
) : BaseViewState

class FeedbackViewModel(
    fetchRoomUseCase: FetchRoomUseCase,
    private val loadFeedbackUseCase: FetchAllFeedbackUseCase
) : BaseViewModel<FeedbackViewState>() {
    init {
        _viewStates.value = FeedbackViewState()
        disposables += fetchRoomUseCase.execute()
            .subscribeBy(
                onSuccess = {
                    _viewStates.value = viewStates.value
                        ?.copy(rooms = Event(
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
                        ))
                },
                onError = {
                    _viewStates.value = viewStates.value
                        ?.copy(rooms = Event(emptyList()))
                }
            )
    }

    fun loadFeedback(room: String, rating: Rating?) {
        disposables += loadFeedbackUseCase.execute(room, rating)
            .doOnSubscribe {
                _viewStates.value = viewStates.value
                    ?.copy(loadingFeedback = true, error = null, feedbacks = null)
            }
            .subscribeBy(
                onError = {
                    _viewStates.value = viewStates.value
                        ?.copy(loadingFeedback = false, error = Event(it), feedbacks = null)
                },
                onSuccess = {
                    _viewStates.value = viewStates.value
                        ?.copy(
                            loadingFeedback = false,
                            error = null,
                            feedbacks = Event(it.map { model ->
                                model.run {
                                    FeedbackPresentationModel(
                                        this.rating,
                                        time,
                                        temperature,
                                        freshness,
                                        humidity,
                                        smell,
                                        cleanliness,
                                        lighting,
                                        sound,
                                        workingAbility
                                    )
                                }
                            })
                        )
                }
            )
    }
}

@Parcelize
data class FeedbackPresentationModel(
    val rating: Rating,
    val time: Long,
    val temperature: Int?,
    val freshness: Int?,
    val humidity: Int?,
    val smell: Float?,
    val cleanliness: Float?,
    val lighting: Int?,
    val sound: Int?,
    val workingAbility: Int?
) : Parcelable
