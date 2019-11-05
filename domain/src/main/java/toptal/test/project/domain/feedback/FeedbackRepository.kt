package toptal.test.project.domain.feedback

import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating

interface FeedbackRepository {

    fun fetchAllFeedbacks(room: String, rating: Rating?): Single<List<FeedbackModel>>
}
