package toptal.test.project.presentation.report

import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import toptal.test.project.common.model.CollectedDataModel
import toptal.test.project.domain.feedback.FetchCollectedDataUseCase
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState
import toptal.test.project.presentation.model.RoomPresentationModel

data class FeedbackDetailViewState(
    val collectedData: List<CollectedDataModel>? = null
) : BaseViewState

class FeedbackDetailViewModel(
    private val fetchCollectedDataUseCase: FetchCollectedDataUseCase
) : BaseViewModel<FeedbackDetailViewState>() {

    init {
        _viewStates.value = FeedbackDetailViewState()
    }
    fun fetchCollectedData(
        roomPresentationModel: RoomPresentationModel,
        feedbackPresentationModel: FeedbackPresentationModel
    ) {
        disposables += fetchCollectedDataUseCase.execute(
            roomPresentationModel.toRoomModel(),
            feedbackPresentationModel.toFeedbackModel()
        ).subscribeBy(
            onError = {},
            onSuccess = {
                _viewStates.value = FeedbackDetailViewState(it)
            }
        )
    }
}
