package toptal.test.project.domain.feedback

import io.reactivex.Single
import toptal.test.project.common.model.DataValueModel
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating
import toptal.test.project.common.model.RoomModel

interface FeedbackRepository {

    fun fetchAllFeedbacks(room: String, rating: Rating?): Single<List<FeedbackModel>>

    fun fetchAirConditionData(room: RoomModel, timestamp: Long): Single<List<DataValueModel>>
}
