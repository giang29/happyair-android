package toptal.test.project.meal.home

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.i_real_time_condition.*
import kotlinx.android.synthetic.main.i_real_time_condition_detail.view.*
import toptal.test.project.common.fullDateTimeFormat
import toptal.test.project.common.model.Rating
import toptal.test.project.common.model.RealTimeConditionModel
import toptal.test.project.meal.R
import kotlin.math.roundToInt

class RealTimeConditionItem(private val realTimeConditionModel: RealTimeConditionModel) :
    Item() {
    override fun getLayout(): Int {
        return R.layout.i_real_time_condition
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.i_real_time_condition_room.text = realTimeConditionModel.getLocalizedString()
        viewHolder.i_real_time_condition_time.text =
            fullDateTimeFormat.format(realTimeConditionModel.timestamp)
        val overAllRating = realTimeConditionModel.getOverAllRating()
        viewHolder.i_real_time_condition_status.text = when (overAllRating) {
            Rating.VERY_GOOD -> "Very Good"
            Rating.GOOD -> "Good"
            Rating.OK -> "OK"
            Rating.BAD -> "Bad"
            Rating.TOO_BAD -> "Very Bad"
            Rating.UNKNOWN -> "OK"
        }
        viewHolder.i_real_time_condition_status.setBackgroundColor(
            Color.parseColor(
                when (overAllRating) {
                    Rating.VERY_GOOD -> "#126B01"
                    Rating.GOOD -> "#46AD2E"
                    Rating.OK -> "#FDD74B"
                    Rating.BAD -> "#FE9B57"
                    Rating.TOO_BAD -> "#FE6A69"
                    Rating.UNKNOWN -> "#FDD74B"
                }
            )
        )
        viewHolder.i_real_time_condition_image_container.setBackgroundColor(
            Color.parseColor(
                when (overAllRating) {
                    Rating.VERY_GOOD -> "#44126B01"
                    Rating.GOOD -> "#4446AD2E"
                    Rating.OK -> "#44FDD74B"
                    Rating.BAD -> "#44FE9B57"
                    Rating.TOO_BAD -> "#44FE6A69"
                    Rating.UNKNOWN -> "#44FDD74B"
                }
            )
        )
        viewHolder.i_real_time_condition_image.setImageResource(
            when (overAllRating) {
                Rating.VERY_GOOD -> R.drawable.ic_very_good
                Rating.GOOD -> R.drawable.ic_good
                Rating.OK -> R.drawable.ic_ok
                Rating.BAD -> R.drawable.ic_bad
                Rating.TOO_BAD -> R.drawable.ic_very_bad
                Rating.UNKNOWN -> R.drawable.ic_ok
            }
        )
        viewHolder.i_real_time_condition_good_list.removeAllViews()
        realTimeConditionModel.good.sortedByDescending { it.getRating().point }.take(3)
            .forEach {
                viewHolder.i_real_time_condition_good_list.addView(
                    LayoutInflater.from(viewHolder.itemView.context)
                        .inflate(
                            R.layout.i_real_time_condition_detail,
                            viewHolder.i_real_time_condition_good_list,
                            false
                        ).apply {
                            i_real_time_condition_detail_key.text = it.type.value
                            i_real_time_condition_detail_value.text = "${it.value.roundToInt()}${it.unit}"
                            background = GradientDrawable().apply {
                                setStroke(
                                    4, Color.parseColor(
                                        when (it.getRating()) {
                                            Rating.VERY_GOOD -> "#126B01"
                                            Rating.GOOD -> "#46AD2E"
                                            Rating.OK -> "#FDD74B"
                                            Rating.BAD -> "#FE9B57"
                                            Rating.TOO_BAD -> "#FE6A69"
                                            Rating.UNKNOWN -> "#FDD74B"
                                        }
                                    )
                                )
                                cornerRadius = 4f.toPx()
                            }
                        }
                )
            }
        realTimeConditionModel.bad.sortedBy { it.getRating().point }.take(3)
            .forEach {
                viewHolder.i_real_time_condition_bad_list.addView(
                    LayoutInflater.from(viewHolder.itemView.context)
                        .inflate(
                            R.layout.i_real_time_condition_detail,
                            viewHolder.i_real_time_condition_bad_list,
                            false
                        ).apply {
                            i_real_time_condition_detail_key.text = it.type.value
                            i_real_time_condition_detail_value.text = "${it.value.roundToInt()}${it.unit}"
                            background = GradientDrawable().apply {
                                setStroke(
                                    4, Color.parseColor(
                                        when (it.getRating()) {
                                            Rating.VERY_GOOD -> "#126B01"
                                            Rating.GOOD -> "#46AD2E"
                                            Rating.OK -> "#FDD74B"
                                            Rating.BAD -> "#FE9B57"
                                            Rating.TOO_BAD -> "#FE6A69"
                                            Rating.UNKNOWN -> "#FDD74B"
                                        }
                                    )
                                )
                                cornerRadius = 4f.toPx()
                            }
                        }
                )
            }
    }
    private fun Float.toPx(): Float = (this * Resources.getSystem().displayMetrics.density)
}
