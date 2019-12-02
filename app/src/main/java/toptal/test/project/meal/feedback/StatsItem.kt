package toptal.test.project.meal.feedback

import android.graphics.Color
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.i_stats.*
import toptal.test.project.common.model.CollectedDataModel
import toptal.test.project.common.model.Rating
import toptal.test.project.meal.R

class StatsItem(private val collectedDataModel: CollectedDataModel) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.i_stats_name.text = collectedDataModel.name
        viewHolder.i_stats_value.text = collectedDataModel.value.toString()

        viewHolder.i_stats_color.setBackgroundColor(
            Color.parseColor(
                when (collectedDataModel.getRating()) {
                    Rating.VERY_GOOD -> "#126B01"
                    Rating.GOOD -> "#46AD2E"
                    Rating.OK -> "#FDD74B"
                    Rating.BAD -> "#FE9B57"
                    Rating.TOO_BAD -> "#FE6A69"
                    Rating.UNKNOWN -> "#FDD74B"
                }
            )
        )
    }

    override fun getLayout(): Int {
        return R.layout.i_stats
    }
}
