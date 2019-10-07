package toptal.test.project.remote.feedback

import toptal.test.project.common.model.FeedbackModel
import kotlin.random.Random

internal val mockFeedbackData: List<FeedbackModel> = run {

    val endTime = 1570476819985L
    val startTime = endTime - 3 * 24 * 60 * 60 * 1000

    (0..200).map {
        val rating = Random.nextInt(-3, 4)
        val timestamp = (Random.nextLong() % (endTime - startTime)) + startTime

        FeedbackModel(rating, timestamp)
    }
}
