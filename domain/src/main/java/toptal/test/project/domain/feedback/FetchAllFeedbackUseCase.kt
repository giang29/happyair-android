package toptal.test.project.domain.feedback

import io.reactivex.Scheduler
import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating

interface FetchAllFeedbackUseCase {
    fun execute(room: String, rating: Rating? = null): Single<List<FeedbackModel>>
}

internal class FetchAllFeedbackUseCaseImpl(
    private val feedbackRepository: FeedbackRepository,
    private val executionThread: Scheduler,
    private val postExecutionThread: Scheduler
) : FetchAllFeedbackUseCase {
    override fun execute(room: String, rating: Rating?): Single<List<FeedbackModel>> {
        return feedbackRepository.fetchAllFeedbacks(room, rating)
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }
}
