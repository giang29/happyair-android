package toptal.test.project.meal.feedback

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.i_stats.*
import toptal.test.project.common.model.CollectedDataModel
import toptal.test.project.meal.R

class StatsItem(private val collectedDataModel: CollectedDataModel) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.i_stats_name.text = collectedDataModel.name
        viewHolder.i_stats_value.text = collectedDataModel.value.toString()
    }

    override fun getLayout(): Int {
        return R.layout.i_stats
    }
}
