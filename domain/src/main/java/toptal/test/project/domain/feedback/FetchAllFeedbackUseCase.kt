package toptal.test.project.domain.feedback

import io.reactivex.Scheduler
import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel

interface FetchAllFeedbackUseCase {
    fun execute(): Single<List<FeedbackModel>>
}

internal class FetchAllFeedbackUseCaseImpl(
    private val feedbackRepository: FeedbackRepository,
    private val executionThread: Scheduler,
    private val postExecutionThread: Scheduler
) : FetchAllFeedbackUseCase {
    override fun execute(): Single<List<FeedbackModel>> {
        return feedbackRepository.fetchAllFeedbacks()
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }
}
