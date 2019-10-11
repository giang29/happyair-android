package toptal.test.project.meal.report

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.forEach
import androidx.core.view.get
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.ValueDataEntry
import kotlinx.android.synthetic.main.f_report.*
import toptal.test.project.common.model.AirDataType
import toptal.test.project.common.model.GroupType
import toptal.test.project.meal.R
import toptal.test.project.meal.base.BaseFragment
import toptal.test.project.meal.report.adapter.RoomAdapter
import toptal.test.project.presentation.report.ReportViewModel
import toptal.test.project.presentation.report.ReportViewState
import java.util.*
import com.anychart.enums.*
import java.text.SimpleDateFormat
import com.anychart.scales.Linear
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding3.widget.checkedChanges


internal class ReportFragment : BaseFragment<ReportViewModel, ReportViewState>() {
    override val viewModel: ReportViewModel by provideViewModel()

    override val layoutResource: Int = R.layout.f_report

    private var selectedDatatype = AirDataType.CO2

    private val cartesian by lazy {
        AnyChart.cartesian()
            .apply {
                xScroller(true)
                animation(true)
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        graph.setChart(cartesian)
        graph.setZoomEnabled(true)

        (chip_group_data_type[0] as Chip).isChecked = true

        chip_group_data_type.forEach { view ->
            (view as Chip).checkedChanges()
                .subscribe {
                    view.isClickable = !it

                    if (it) {
                        selectedDatatype = when (view.text.toString().toUpperCase(Locale.ROOT)) {
                            AirDataType.CO2.toString() -> AirDataType.CO2
                            AirDataType.TEMPERATURE.toString() -> AirDataType.TEMPERATURE
                            else -> throw IllegalArgumentException(view.text.toString())
                        }
                        (room_spinner.selectedItem as? String)?.run {
                            viewModel.loadReport(
                                this,
                                selectedDatatype,
                                Calendar.getInstance().apply {
                                    timeInMillis =
                                        System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000
                                },
                                Calendar.getInstance(),
                                GroupType.DAILY
                            )
                        }
                    }
                }
        }
    }

    override fun onStateChanged(viewState: ReportViewState) {
        when (viewState) {
            is ReportViewState.Initial -> {
                room_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.loadReport(
                            viewState.rooms[position],
                            selectedDatatype,
                            Calendar.getInstance().apply { timeInMillis = System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000 },
                            Calendar.getInstance(),
                            GroupType.DAILY
                        )
                    }
                }
                room_spinner.adapter = RoomAdapter(requireContext(), viewState.rooms)
                room_spinner.setSelection(0)
            }
            is ReportViewState.Success -> {
                val reports = viewState.reportListModel.data
                cartesian.removeAllSeries()
                cartesian.legend().enabled(true)

                APIlib.getInstance().setActiveAnyChartView(graph)

                cartesian.animation(true)
                val columnData = reports.map {
                    ValueDataEntry(formatDate(viewState.reportListModel.groupBy, it.collectedTime), it.value)
                }

                val lineData = reports.map {
                    ValueDataEntry(formatDate(viewState.reportListModel.groupBy, it.collectedTime), it.rating)
                }

                val max = reports.map { it.value }.max() ?: 0f
                cartesian.column(columnData).name(viewState.reportListModel.type.toString())
                    .fill(
                        "function() {" +
                                "var max = ${max};\n" +
                                "if (this.value > max*4/5 + max/5) return '#f56566';\n" +
                                "if (this.value > max*3/5 + max/5) return 'orange';\n" +
                                "if (this.value > max*2/5 + max/5) return 'yellow';\n" +
                                "return '#7dc206';}"
                    )
                    .legendItem().enabled(false)

                val scalesLinear = Linear.instantiate()
                scalesLinear.minimum(1.0f)
                scalesLinear.maximum(5.0f)
                scalesLinear.ticks("{ interval: 1 }")

                val extraYAxis = cartesian.yAxis(1)
                extraYAxis.orientation(Orientation.RIGHT)
                    .scale(scalesLinear)

                val line = cartesian.line(lineData)
                line.name("Average Rating")
                line.yScale(scalesLinear)

                cartesian.yScale().minimum(0.0)

                cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: } ${viewState.reportListModel.unit}")

                cartesian.xAxis(0).title("Timeline")
            }
        }
    }

    private fun formatDate(groupBy: GroupType, calendar: Calendar): String {
        return when (groupBy) {
            GroupType.DAILY -> SimpleDateFormat("MMM d yy").format(calendar.time)
            GroupType.MONTHLY -> SimpleDateFormat("MMM yy").format(calendar.time)
            GroupType.WEEKLY -> SimpleDateFormat("w yy").format(calendar.time)
            GroupType.UNKNOWN -> throw IllegalArgumentException()
        }
    }

    companion object {
        fun newInstance() = ReportFragment()
    }
}
