package toptal.test.project.presentation.report

import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating
import toptal.test.project.common.model.RoomModel
import toptal.test.project.domain.feedback.FetchAllFeedbackUseCase
import toptal.test.project.domain.room.FetchRoomUseCase
import toptal.test.project.presentation.Event
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState

data class FeedbackViewState(
    val rooms: Event<List<RoomModel>>? = null,
    val loadingFeedback: Boolean = false,
    val error: Event<Throwable>? = null,
    val feedbacks: Event<List<FeedbackModel>>? = null
): BaseViewState

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
                        ?.copy(rooms = Event(it))
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
                        ?.copy(loadingFeedback = false, error = null, feedbacks = Event(it))
                }
            )
    }
}
