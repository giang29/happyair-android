package toptal.test.project.meal.feedback

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.i_feedback.*
import toptal.test.project.common.dateTimeDateFormat
import toptal.test.project.common.model.FeedbackModel
import toptal.test.project.meal.R

class FeedbackItem(private val feedbackModel: FeedbackModel) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.i_feedback_rating.text = feedbackModel.rating.toString()
        viewHolder.i_feedback_date.text = dateTimeDateFormat.format(feedbackModel.time)
    }

    override fun getLayout(): Int {
        return R.layout.i_feedback
    }
}
