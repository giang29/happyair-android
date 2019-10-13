package toptal.test.project.remote.feedback

import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.common.model.Rating
import kotlin.random.Random

internal fun generateFeedbackData(rating: Rating? = null): List<FeedbackModel> {
    val endTime = 1570476819985L
    val startTime = endTime - 3 * 24 * 60 * 60 * 1000

    return (0..200).map {
        val randomizedRating = rating ?: Rating.values()[Random.nextInt(Rating.values().size)]
        val timestamp = (Random.nextLong() % (endTime - startTime)) + startTime

        FeedbackModel(randomizedRating, timestamp)
    }.sortedByDescending { it.time }
}
