package toptal.test.project.presentation.report

import android.os.Parcelable
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.parcel.Parcelize
import toptal.test.project.common.model.Rating
import toptal.test.project.common.model.ValueModel
import toptal.test.project.domain.feedback.FetchAllFeedbackUseCase
import toptal.test.project.domain.room.FetchRoomUseCase
import toptal.test.project.presentation.Event
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState
import toptal.test.project.presentation.model.RoomPresentationModel
import java.util.*
import java.util.concurrent.TimeUnit

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
    private val relay = PublishRelay.create<Pair<String, Rating?>>()

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
        disposables += relay.throttleFirst(100, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                loadFeedbackUseCase.execute(it.first, it.second)
                    .doOnSubscribe {
                        _viewStates.value = viewStates.value
                            ?.copy(loadingFeedback = true, error = null)
                    }
                    .toObservable()
            }
            .subscribeBy(
                onError = {
                    _viewStates.value = viewStates.value
                        ?.copy(loadingFeedback = false, error = Event(it), feedbacks = null)
                },
                onNext = {
                    _viewStates.value = viewStates.value
                        ?.copy(
                            loadingFeedback = false,
                            error = null,
                            feedbacks = Event(it.map { model ->
                                model.run {
                                    FeedbackPresentationModel(
                                        this.rating,
                                        time,
                                        temperature?.toValuePresentationModel(),
                                        freshness?.toValuePresentationModel(),
                                        humidity?.toValuePresentationModel(),
                                        smell?.toValuePresentationModel(),
                                        cleanliness?.toValuePresentationModel(),
                                        lighting?.toValuePresentationModel(),
                                        sound?.toValuePresentationModel(),
                                        workingAbility?.toValuePresentationModel()
                                    )
                                }
                            })
                        )
                }
            )
    }

    fun loadFeedback(room: String, rating: Rating?) {
        relay.accept(Pair(room, rating))
    }
}

@Parcelize
data class ValuePresentationModel(
    val value: Float,
    val answer: Map<String, String>?
): Parcelable {
    fun getLocalizedString(): String? {
        return answer?.get(Locale.getDefault().language.toLowerCase(Locale.ROOT))
            ?: answer?.get("fi")
    }
}

private fun ValueModel.toValuePresentationModel(): ValuePresentationModel {
    return ValuePresentationModel(
        value,
        answer
    )
}

@Parcelize
data class FeedbackPresentationModel(
    val rating: Rating,
    val time: Long,
    val temperature: ValuePresentationModel?,
    val freshness: ValuePresentationModel?,
    val humidity: ValuePresentationModel?,
    val smell: ValuePresentationModel?,
    val cleanliness: ValuePresentationModel?,
    val lighting: ValuePresentationModel?,
    val sound: ValuePresentationModel?,
    val workingAbility: ValuePresentationModel?
) : Parcelable
