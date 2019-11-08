package toptal.test.project.meal.feedback

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.forEach
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
import toptal.test.project.meal.home.HomeActivity
import toptal.test.project.meal.report.adapter.RoomAdapter
import toptal.test.project.presentation.model.RoomPresentationModel
import toptal.test.project.presentation.report.FeedbackViewModel
import toptal.test.project.presentation.report.FeedbackViewState

internal class FeedbackFragment : BaseFragment<FeedbackViewModel, FeedbackViewState>() {
    override val viewModel: FeedbackViewModel by provideViewModel()

    override val layoutResource: Int = R.layout.f_feedback

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private var selectedRating: Rating? = null
    private var selectedRoom: RoomPresentationModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        f_feedback_chip_group_data_type.addView(
            (layoutInflater.inflate(
                R.layout.i_chip,
                f_feedback_chip_group_data_type,
                false
            ) as Chip).apply {
                tag = null
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
                    tag = rating
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
        f_feedback_chip_group_data_type.forEach {
            if (it is Chip && (it.tag as? Rating) == selectedRating) {
                it.isChecked = true
                return@forEach
            }
        }
        adapter.setOnItemClickListener { item, _ ->
            val feedback = (item as? FeedbackItem)?.feedbackModel
            val room = (f_feedback_room_spinner.selectedItem as? RoomPresentationModel)
            if (feedback != null && room != null) {
                (activity as? HomeActivity)?.onNavigateToFeedbackDetail(feedback, room)
            }
        }
        f_feedback_list.adapter = adapter
        f_feedback_list.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
    }

    override fun onStateChanged(viewState: FeedbackViewState): Boolean {
        viewState.rooms?.run {
            if (isInitialized)
                getContentIfNotHandledOrReturnNull()
            else
                peekContent()
        }?.run {
            f_feedback_room_spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedRoom = get(position)
                        viewModel.loadFeedback(get(position).id, selectedRating)
                    }
                }
            f_feedback_room_spinner.adapter = RoomAdapter(requireContext(),  this)
            val index = indexOf(selectedRoom)
            f_feedback_room_spinner.setSelection(kotlin.math.max(index, 0))
        }

        viewState.feedbacks?.run {
            if (isInitialized)
                getContentIfNotHandledOrReturnNull()
            else
                peekContent()
        }?.run {
            adapter.update(
                map { FeedbackItem(it) }
            )
        }
        return super.onStateChanged(viewState)
    }
}
