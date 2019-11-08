package toptal.test.project.remote.feedback

import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating
import toptal.test.project.data.feedback.FeedbackRemoteDataStore
import toptal.test.project.remote.HappyAirGateway

internal class FeedbackRemoteDataStoreImpl(
    private val happyAirGateway: HappyAirGateway
) : FeedbackRemoteDataStore {
    override fun fetchAllFeedback(room: String, rating: Rating?): Single<List<FeedbackModel>> {
        return happyAirGateway.getFeedbacks(
            room,
            when (rating) {
                Rating.TOO_BAD -> 1.5f
                Rating.BAD -> 2.75f
                Rating.OK -> 3.75f
                Rating.GOOD -> 4.25f
                Rating.VERY_GOOD -> 5f
                else -> 5f
            }
        ).map {
            it.ratings
                .filter { ratingRemoteModel ->
                    ratingRemoteModel.rating in when (rating) {
                        Rating.TOO_BAD -> 0f..1.5f
                        Rating.BAD -> 1.51f..2.75f
                        Rating.OK -> 2.76f..3.75f
                        Rating.GOOD -> 3.76f..4.25f
                        Rating.VERY_GOOD -> 4.26f..5f
                        else -> 0f..5f
                    }
                }
                .map { ratingRemoteModel ->
                    FeedbackModel(
                        when (ratingRemoteModel.rating) {
                            in 0f..1.5f -> Rating.TOO_BAD
                            in 1.5f..2.75f -> Rating.BAD
                            in 2.75f..3.75f -> Rating.OK
                            in 3.75f..4.25f -> Rating.GOOD
                            in 4.25f..5f -> Rating.VERY_GOOD
                            else -> Rating.VERY_GOOD
                        },
                        ratingRemoteModel.timestamp,
                        ratingRemoteModel.temperature,
                        ratingRemoteModel.freshness,
                        ratingRemoteModel.humidity,
                        ratingRemoteModel.smell,
                        ratingRemoteModel.cleanliness,
                        ratingRemoteModel.lighting,
                        ratingRemoteModel.sound,
                        ratingRemoteModel.workingAbility
                    )
                }
                .sortedByDescending { ratingRemoteModel -> ratingRemoteModel.time }
        }
    }
}
