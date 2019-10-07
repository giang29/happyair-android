package toptal.test.project.data.feedback

import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.domain.feedback.FeedbackRepository

internal class FeedbackRepositoryImpl(
    private val feedbackRemoteDataStore: FeedbackRemoteDataStore
): FeedbackRepository {
    override fun fetchAllFeedbacks(): Single<List<FeedbackModel>> {
        return feedbackRemoteDataStore.fetchAllFeedback()
    }
}
