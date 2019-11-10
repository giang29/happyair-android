package toptal.test.project.domain.feedback

import io.reactivex.Scheduler
import io.reactivex.Single
import toptal.test.project.common.model.CollectedDataModel
import toptal.test.project.common.model.DataValueModel
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.RoomModel

interface FetchCollectedDataUseCase {
    fun execute(roomModel: RoomModel, feedbackModel: FeedbackModel):
            Single<List<CollectedDataModel>>
}

internal class FetchCollectedDataUseCaseImpl(
    private val feedbackRepository: FeedbackRepository,
    private val executionThread: Scheduler,
    private val postExecutionThread: Scheduler
) : FetchCollectedDataUseCase {
    override fun execute(
        roomModel: RoomModel,
        feedbackModel: FeedbackModel
    ): Single<List<CollectedDataModel>> {
        return feedbackRepository.fetchAirConditionData(roomModel, feedbackModel.time)
            .map { values ->
                values.groupBy { it.dataPointID }
                    .map { (k, v) ->
                        when (k) {
                            roomModel.co2 -> findLatestValue(v)?.let {
                                CollectedDataModel(
                                    "CO2",
                                    it
                                )
                            }
                            roomModel.humidity -> findLatestValue(v)?.let {
                                CollectedDataModel(
                                    "Humidity",
                                    it
                                )
                            }
                            roomModel.pm1 -> findLatestValue(v)?.let {
                                CollectedDataModel(
                                    "PM1",
                                    it
                                )
                            }
                            roomModel.pm10 -> findLatestValue(v)?.let {
                                CollectedDataModel(
                                    "PM10",
                                    it
                                )
                            }
                            roomModel.pm2_5 -> findLatestValue(v)?.let {
                                CollectedDataModel(
                                    "PM2.5",
                                    it
                                )
                            }
                            roomModel.pressureDiff -> findLatestValue(v)?.let {
                                CollectedDataModel(
                                    "Pressure Difference",
                                    it
                                )
                            }
                            roomModel.temperature -> findLatestValue(v)?.let {
                                CollectedDataModel(
                                    "Temperature",
                                    it
                                )
                            }
                            roomModel.tvoc -> findLatestValue(v)?.let {
                                CollectedDataModel(
                                    "TVOC",
                                    it
                                )
                            }
                            else -> null
                        }
                    }
                    .filterNotNull()
            }
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }

    private fun findLatestValue(data: List<DataValueModel>): Float? {
        return data.maxBy { it.timestamp }?.value
    }
}
