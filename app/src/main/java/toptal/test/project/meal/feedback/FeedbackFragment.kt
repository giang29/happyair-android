package toptal.test.project.meal.feedback

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.f_feedback.*
import toptal.test.project.common.model.Rating
import toptal.test.project.meal.R
import toptal.test.project.meal.base.BaseFragment
import toptal.test.project.meal.report.adapter.RoomAdapter
import toptal.test.project.presentation.model.RoomPresentationModel
import toptal.test.project.presentation.report.FeedbackViewModel
import toptal.test.project.presentation.report.FeedbackViewState

internal class FeedbackFragment : BaseFragment<FeedbackViewModel, FeedbackViewState>() {
    override val viewModel: FeedbackViewModel by provideViewModel()

    override val layoutResource: Int = R.layout.f_feedback

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private var selectedRating: Rating? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        f_feedback_chip_group_data_type.addView(
            (layoutInflater.inflate(
                R.layout.i_chip,
                f_feedback_chip_group_data_type,
                false
            ) as Chip).apply {
                text = getString(R.string.all)
                checkedChanges()
                    .subscribe {
                        isClickable = !it
                        if (it) {
                            selectedRating = null
                            (f_feedback_room_spinner.selectedItem as? RoomPresentationModel)?.run {
                                viewModel.loadFeedback(id, null)
                            }
                        }
                    }
            }
        )
        Rating.values().filter { it != Rating.UNKNOWN }.forEach { rating ->
            f_feedback_chip_group_data_type.addView(
                (layoutInflater.inflate(
                    R.layout.i_chip,
                    f_feedback_chip_group_data_type,
                    false
                ) as Chip).apply {
                    text = rating.toString()
                    checkedChanges()
                        .subscribe {
                            isClickable = !it
                            if (it) {
                                selectedRating = rating
                                (f_feedback_room_spinner.selectedItem as? RoomPresentationModel)?.run {
                                    viewModel.loadFeedback(id, rating)
                                }
                            }
                        }
                }
            )
        }
        (f_feedback_chip_group_data_type[0] as Chip).isChecked = true
        f_feedback_list.adapter = adapter
        f_feedback_list.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
    }

    override fun onStateChanged(viewState: FeedbackViewState) {
        viewState.rooms?.getContentIfNotHandledOrReturnNull()?.run {
            f_feedback_room_spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.loadFeedback(get(position).id, selectedRating)
                    }
                }
            f_feedback_room_spinner.adapter = RoomAdapter(requireContext(),  this)
            f_feedback_room_spinner.setSelection(0)
        }

        viewState.feedbacks?.getContentIfNotHandledOrReturnNull()?.run {
            adapter.update(
                map { FeedbackItem(it) }
            )
        }
    }
}
