package toptal.test.project.data.feedback

import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating

interface FeedbackRemoteDataStore {

    fun fetchAllFeedback(room: String, rating: Rating?): Single<List<FeedbackModel>>
}
