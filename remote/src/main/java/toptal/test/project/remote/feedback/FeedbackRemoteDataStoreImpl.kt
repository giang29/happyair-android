package toptal.test.project.remote.feedback

import io.reactivex.Single
import toptal.test.project.common.model.DataValueModel
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating
import toptal.test.project.common.nuukaDateFormat
import toptal.test.project.common.nuukaResponseDateFormat
import toptal.test.project.data.feedback.FeedbackRemoteDataStore
import toptal.test.project.remote.HappyAirGateway
import toptal.test.project.remote.NuukaService

internal class FeedbackRemoteDataStoreImpl(
    private val happyAirGateway: HappyAirGateway,
    private val nuukaService: NuukaService
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

    override fun fetchAirConditionData(
        dataPointIds: List<Int>,
        timestamp: Long
    ): Single<List<DataValueModel>> {
        return nuukaService.getCollectedData(
            dataPointIds.joinToString(","),
            nuukaDateFormat.format(timestamp - 60 * 60 * 1000L),
            nuukaDateFormat.format(timestamp)
        ).map {
            it.map { model ->
                DataValueModel(
                    nuukaResponseDateFormat.parse(model.Timestamp).time,
                    model.Value,
                    model.DataPointID
                )
            }
        }
    }
}
