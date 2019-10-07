package toptal.test.project.data.feedback

import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel

interface FeedbackRemoteDataStore {

    fun fetchAllFeedback(): Single<List<FeedbackModel>>
}
