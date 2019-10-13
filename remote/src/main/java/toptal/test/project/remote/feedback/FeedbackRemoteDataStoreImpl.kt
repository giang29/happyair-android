package toptal.test.project.remote.feedback

import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating
import toptal.test.project.data.feedback.FeedbackRemoteDataStore

internal class FeedbackRemoteDataStoreImpl: FeedbackRemoteDataStore {
    override fun fetchAllFeedback(rating: Rating?): Single<List<FeedbackModel>> {
        return Single.just(generateFeedbackData(rating))
    }
}
