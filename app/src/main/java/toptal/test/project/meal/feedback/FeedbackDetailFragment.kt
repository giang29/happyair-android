package toptal.test.project.meal.feedback

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.f_feedback_detail.*
import toptal.test.project.common.dateTimeDateFormat
import toptal.test.project.common.model.Rating
import toptal.test.project.meal.R
import toptal.test.project.meal.base.StatelessBaseFragment


internal class FeedbackDetailFragment : StatelessBaseFragment() {
    override val layoutResource: Int = R.layout.f_feedback_detail

    private val args: FeedbackDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        f_fragment_detail_toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        args.feedback.run {
            f_feedback_detail_icon.setImageResource(
                when (rating) {
                    Rating.TOO_BAD -> R.drawable.ic_very_bad
                    Rating.BAD -> R.drawable.ic_bad
                    Rating.OK -> R.drawable.ic_ok
                    Rating.GOOD -> R.drawable.ic_good
                    Rating.VERY_GOOD -> R.drawable.ic_very_good
                    Rating.UNKNOWN -> R.drawable.ic_very_good
                }
            )
            f_feedback_detail_rating.text = rating.toString()
            f_feedback_detail_date.text = dateTimeDateFormat.format(time)

            temperature?.run {
                group_temp.isVisible = true
                f_feed_detail_icon_temp.setImageResource(getRating().getIcon())
                f_feed_detail_temp.text = getRating().toString()
            } ?: run { group_temp.isVisible = false }
            freshness?.run {
                group_fresh.isVisible = true
                f_feed_detail_icon_fresh.setImageResource(getRating().getIcon())
                f_feed_detail_fresh.text = getRating().toString()
            } ?: run { group_fresh.isVisible = false }
            humidity?.run {
                group_humid.isVisible = true
                f_feed_detail_icon_humid.setImageResource(getRating().getIcon())
                f_feed_detail_humid.text = getRating().toString()
            } ?: run { group_humid.isVisible = false }
            smell?.run {
                group_smell.isVisible = true
                f_feed_detail_icon_smell.setImageResource(getRating().getIcon())
                f_feed_detail_smell.text = getRating().toString()
            } ?: run { group_smell.isVisible = false }
            workingAbility?.run {
                group_wa.isVisible = true
                f_feed_detail_icon_work.setImageResource(getRating().getIcon())
                f_feed_detail_wa.text = getRating().toString()
            } ?: run { group_wa.isVisible = false }
            lighting?.run {
                group_lighting.isVisible = true
                f_feed_detail_icon_light.setImageResource(getRating().getIcon())
                f_feed_detail_light.text = getRating().toString()
            } ?: run { group_lighting.isVisible = false }
            cleanliness?.run {
                group_clean.isVisible = true
                f_feed_detail_icon_clean.setImageResource(getRating().getIcon())
                f_feed_detail_clean.text = getRating().toString()
            } ?: run { group_clean.isVisible = false }
            sound?.run {
                group_sound.isVisible = true
                f_feed_detail_icon_sound.setImageResource(getRating().getIcon())
                f_feed_detail_sound.text = getRating().toString()
            } ?: run { group_sound.isVisible = false }
        }
    }

    private fun Float.getRating(): Rating {
        return when (this) {
            in 0f..1.5f -> Rating.TOO_BAD
            in 1.5f..2.75f -> Rating.BAD
            in 2.75f..3.75f -> Rating.OK
            in 3.75f..4.25f -> Rating.GOOD
            in 4.25f..5f -> Rating.VERY_GOOD
            else -> Rating.VERY_GOOD
        }
    }

    private fun Int.getRating(): Rating {
        return toFloat().getRating()
    }

    private fun Rating.getIcon(): Int {
        return when (this) {
            Rating.TOO_BAD -> R.drawable.ic_very_bad
            Rating.BAD -> R.drawable.ic_bad
            Rating.OK -> R.drawable.ic_ok
            Rating.GOOD -> R.drawable.ic_good
            Rating.VERY_GOOD -> R.drawable.ic_very_good
            Rating.UNKNOWN -> R.drawable.ic_very_good
        }
    }
}
