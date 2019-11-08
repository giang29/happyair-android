package toptal.test.project.remote.feedback

import com.squareup.moshi.JsonClass
import toptal.test.project.common.model.ValueModel

@JsonClass(generateAdapter = true)
data class RatingRemoteModel(
    val rating: Float,
    val timestamp: Long,
    val temperature: ValueModel?,
    val freshness: ValueModel?,
    val humidity: ValueModel?,
    val smell: ValueModel?,
    val cleanliness: ValueModel?,
    val lighting: ValueModel?,
    val sound: ValueModel?,
    val workingAbility: ValueModel?
)
