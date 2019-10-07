package toptal.test.project.domain.feedback

import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel

interface FeedbackRepository {

    fun fetchAllFeedbacks(): Single<List<FeedbackModel>>
}
