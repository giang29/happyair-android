package toptal.test.project.meal.home


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.f_real_time_condition.*

import toptal.test.project.meal.R
import toptal.test.project.meal.base.BaseFragment
import toptal.test.project.presentation.report.RealtimeReportViewModel
import toptal.test.project.presentation.report.RealtimeReportViewState

/**
 * A simple [Fragment] subclass.
 */
internal class RealTimeConditionFragment :
    BaseFragment<RealtimeReportViewModel, RealtimeReportViewState>() {
    override val viewModel: RealtimeReportViewModel by provideViewModel()
    override val layoutResource: Int = R.layout.f_real_time_condition

    private val adapter = GroupAdapter<GroupieViewHolder>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        f_real_time_condition_list.adapter = adapter
        f_real_time_condition_list.layoutManager =
            LinearLayoutManager(requireContext(), VERTICAL, false)
    }

    override fun onStateChanged(viewState: RealtimeReportViewState): Boolean {

        adapter.update(
            viewState.reports.map {
                RealTimeConditionItem(it)
            }
        )

        return super.onStateChanged(viewState)
    }
}
