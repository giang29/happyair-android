package toptal.test.project.meal.feedback

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.f_feedback_detail.*
import toptal.test.project.common.dateTimeDateFormat
import toptal.test.project.common.model.Rating
import toptal.test.project.meal.R
import toptal.test.project.meal.base.BaseFragment
import toptal.test.project.presentation.report.FeedbackDetailViewModel
import toptal.test.project.presentation.report.FeedbackDetailViewState
import java.util.*


internal class FeedbackDetailFragment : BaseFragment<FeedbackDetailViewModel, FeedbackDetailViewState>() {
    override val viewModel: FeedbackDetailViewModel by provideViewModel()

    private val adapter = GroupAdapter<GroupieViewHolder>()
    override fun onStateChanged(viewState: FeedbackDetailViewState): Boolean {
        viewState.collectedData?.map {
            StatsItem(it)
        }?.run(adapter::update)
        return super.onStateChanged(viewState)
    }

    override val layoutResource: Int = R.layout.f_feedback_detail

    private val args: FeedbackDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        f_fragment_detail_toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        f_feed_detail_stats_list.layoutManager = GridLayoutManager(requireContext(), 2, HORIZONTAL, false)
        f_feed_detail_stats_list.adapter = adapter
        viewModel.fetchCollectedData(args.room, args.feedback)
        args.feedback.run {
            f_feedback_detail_icon.setImageResource(
                rating.getIcon()
            )
            f_feedback_detail_rating.text = rating.toString()
            f_feedback_detail_date.text = dateTimeDateFormat.format(time)

            temperature?.run {
                group_temp.isVisible = true
                f_feed_detail_icon_temp.setImageResource(value.getRating().getIcon())
                f_feed_detail_temp.text = getLocalizedString()?.toLowerCase(Locale.ROOT)?.capitalize() 
                    ?: value.getRating().toString()
            } ?: run { group_temp.isVisible = false }
            freshness?.run {
                group_fresh.isVisible = true
                f_feed_detail_icon_fresh.setImageResource(value.getRating().getIcon())
                f_feed_detail_fresh.text = getLocalizedString()?.toLowerCase(Locale.ROOT)?.capitalize() 
                    ?: value.getRating().toString()
            } ?: run { group_fresh.isVisible = false }
            humidity?.run {
                group_humid.isVisible = true
                f_feed_detail_icon_humid.setImageResource(value.getRating().getIcon())
                f_feed_detail_humid.text = getLocalizedString()?.toLowerCase(Locale.ROOT)?.capitalize()
                    ?: value.getRating().toString()
            } ?: run { group_humid.isVisible = false }
            smell?.run {
                group_smell.isVisible = true
                f_feed_detail_icon_smell.setImageResource(value.getRating().getIcon())
                f_feed_detail_smell.text = getLocalizedString()?.toLowerCase(Locale.ROOT)?.capitalize() 
                    ?: value.getRating().toString()
            } ?: run { group_smell.isVisible = false }
            workingAbility?.run {
                group_wa.isVisible = true
                f_feed_detail_icon_work.setImageResource(value.getRating().getIcon())
                f_feed_detail_wa.text = getLocalizedString()?.toLowerCase(Locale.ROOT)?.capitalize() 
                    ?: value.getRating().toString()
            } ?: run { group_wa.isVisible = false }
            lighting?.run {
                group_lighting.isVisible = true
                f_feed_detail_icon_light.setImageResource(value.getRating().getIcon())
                f_feed_detail_light.text = getLocalizedString()?.toLowerCase(Locale.ROOT)?.capitalize() 
                    ?: value.getRating().toString()
            } ?: run { group_lighting.isVisible = false }
            cleanliness?.run {
                group_clean.isVisible = true
                f_feed_detail_icon_clean.setImageResource(value.getRating().getIcon())
                f_feed_detail_clean.text = getLocalizedString()?.toLowerCase(Locale.ROOT)?.capitalize() 
                    ?: value.getRating().toString()
            } ?: run { group_clean.isVisible = false }
            sound?.run {
                group_sound.isVisible = true
                f_feed_detail_icon_sound.setImageResource(value.getRating().getIcon())
                f_feed_detail_sound.text = getLocalizedString()?.toLowerCase(Locale.ROOT)?.capitalize() 
                    ?: value.getRating().toString()
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
