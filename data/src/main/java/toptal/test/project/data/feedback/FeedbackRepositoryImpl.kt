package toptal.test.project.data.feedback

import io.reactivex.Single
import toptal.test.project.common.model.DataValueModel
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating
import toptal.test.project.common.model.RoomModel
import toptal.test.project.domain.feedback.FeedbackRepository

internal class FeedbackRepositoryImpl(
    private val feedbackRemoteDataStore: FeedbackRemoteDataStore
): FeedbackRepository {
    override fun fetchAllFeedbacks(room: String, rating: Rating?): Single<List<FeedbackModel>> {
        return feedbackRemoteDataStore.fetchAllFeedback(room, rating)
    }

    override fun fetchAirConditionData(
        room: RoomModel,
        timestamp: Long
    ): Single<List<DataValueModel>> {
        return feedbackRemoteDataStore.fetchAirConditionData(
            room.dataPointIds,
            timestamp
        )
    }
}
