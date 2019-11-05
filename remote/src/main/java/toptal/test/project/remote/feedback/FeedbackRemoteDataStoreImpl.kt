package toptal.test.project.remote.feedback

import io.reactivex.Single
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating
import toptal.test.project.data.feedback.FeedbackRemoteDataStore
import toptal.test.project.remote.HappyAirGateway

internal class FeedbackRemoteDataStoreImpl(
    private val happyAirGateway: HappyAirGateway
): FeedbackRemoteDataStore {
    override fun fetchAllFeedback(room: String, rating: Rating?): Single<List<FeedbackModel>> {
        return happyAirGateway.getFeedbacks(
            room,
            when(rating) {
                Rating.TOO_BAD -> 1f
                Rating.BAD -> 2f
                Rating.OK -> 3f
                Rating.GOOD -> 4f
                Rating.VERY_GOOD -> 5f
                else -> 5f
            }
        ).map {
            it.ratings
                .filter {ratingRemoteModel ->
                    ratingRemoteModel.rating in 0f..5f
                }
                .map { ratingRemoteModel ->
                    FeedbackModel(
                        when(ratingRemoteModel.rating) {
                            0f, in 0f to 1f -> Rating.TOO_BAD
                            in 1f to 2f -> Rating.BAD
                            in 2f to 3f -> Rating.OK
                            in 3f to 4f -> Rating.GOOD
                            in 4f to 5f -> Rating.VERY_GOOD
                            else -> Rating.VERY_GOOD
                        },
                        ratingRemoteModel.timestamp
                    )
                }
        }
    }
}

data class SemiOpenFloatRange(val from: Float, val to: Float)
infix fun Float.to(to: Float) = SemiOpenFloatRange(this, to)
operator fun SemiOpenFloatRange.contains(f: Float) = from < f && f <= to
