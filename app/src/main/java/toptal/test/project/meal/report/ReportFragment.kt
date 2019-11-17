package toptal.test.project.meal.report

import android.annotation.SuppressLint
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
import com.anychart.graphics.vector.StrokeLineCap
import com.anychart.graphics.vector.StrokeLineJoin
import com.anychart.scales.Linear
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import toptal.test.project.common.dateMonthDateFormat
import toptal.test.project.common.fullDateFormat
import toptal.test.project.common.model.ReportModel
import toptal.test.project.common.shortDateFormat
import toptal.test.project.common.weekFormat
import toptal.test.project.meal.base.clicksThrottle
import toptal.test.project.meal.common.SublimePickerDialogFragment
import toptal.test.project.presentation.model.RoomPresentationModel


internal class ReportFragment :
    BaseFragment<ReportViewModel, ReportViewState>(),
    SublimePickerDialogFragment.DateRangePickedListener {

    override val viewModel: ReportViewModel by provideViewModel()

    override val layoutResource: Int = R.layout.f_report

    private var selectedDatatype = AirDataType.CO2

    private var startDate: Calendar = Calendar.getInstance().apply {
        timeInMillis =
            System.currentTimeMillis() - 7L * 24 * 60 * 60 * 1000
    }

    private var endDate: Calendar = Calendar.getInstance()

    private val cartesian by lazy {
        AnyChart.cartesian()
            .apply {
                xScroller(true)
            }
    }

    private fun togglePositionToGroupType(position: Int): GroupType {
        return when (position) {
            0 -> GroupType.DAILY
            1 -> GroupType.WEEKLY
            2 -> GroupType.MONTHLY
            else -> GroupType.UNKNOWN
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        f_report_date_range.text =
            "${dateMonthDateFormat.format(startDate.timeInMillis)} - ${dateMonthDateFormat.format(endDate.timeInMillis)}"
        f_report_date_range.clicksThrottle()
            .subscribe {
                SublimePickerDialogFragment.newInstance(startDate.timeInMillis, endDate.timeInMillis)
                    .show(childFragmentManager, "date_range")
            }

        f_report_graph.setChart(cartesian)
        f_report_graph.setZoomEnabled(true)

        (f_report_chip_group_data_type[0] as Chip).isChecked = true
        f_report_toggle_switch.setCheckedPosition(0)
        f_report_toggle_switch.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(position: Int) {
                onFilterChanged()
            }
        }

        f_report_chip_group_data_type.forEach { v ->
            (v as Chip).checkedChanges()
                .subscribe {
                    v.isClickable = !it

                    if (it) {
                        selectedDatatype = when (v.id) {
                            R.id.f_report_chip_co2 -> AirDataType.CO2
                            R.id.f_report_chip_temp -> AirDataType.TEMPERATURE
                            R.id.f_report_chip_pm1 -> AirDataType.PM1
                            R.id.f_report_chip_pm10 -> AirDataType.PM10
                            R.id.f_report_chip_pm25 -> AirDataType.PM2_5
                            R.id.f_report_chip_pressure_diff -> AirDataType.PRESSURE_DIFF
                            R.id.f_report_chip_tvoc -> AirDataType.TVOC
                            R.id.f_report_chip_humidity -> AirDataType.HUMIDITY
                            else -> throw IllegalArgumentException(v.text.toString())
                        }
                        onFilterChanged()
                    }
                }
        }
    }

    private fun onFilterChanged() {
        (f_report_room_spinner.selectedItem as? RoomPresentationModel)?.run {
            viewModel.loadReport(
                id,
                selectedDatatype,
                startDate,
                endDate,
                togglePositionToGroupType(
                    f_report_toggle_switch.checkedPosition ?: 0
                )
            )
        }
    }

    override fun onDateRangePicked(startDate: Calendar, endDate: Calendar) {
        this.startDate = startDate
        this.endDate = endDate
        f_report_date_range.text =
            "${dateMonthDateFormat.format(startDate.timeInMillis)} - ${dateMonthDateFormat.format(endDate.timeInMillis)}"
        onFilterChanged()
    }

    override fun onStateChanged(viewState: ReportViewState): Boolean {
        viewState.rooms?.run {
            if (isInitialized)
                getContentIfNotHandledOrReturnNull()
            else
                peekContent()
        }?.run {
            f_report_room_spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        onFilterChanged()
                    }
                }
            f_report_room_spinner.adapter = RoomAdapter(requireContext(), this)
            f_report_room_spinner.setSelection(0)
        }

        viewState.reportListModel?.run {
            if (isInitialized)
                getContentIfNotHandledOrReturnNull()
            else
                peekContent()
        }?.run {
            val reports = data
            cartesian.removeAllSeries()
            cartesian.legend().enabled(true)

            APIlib.getInstance().setActiveAnyChartView(f_report_graph)

            cartesian.animation(true)
            val columnData = reports.map {
                ValueDataEntry(formatDate(groupBy, it.collectedTime), it.value)
            }

            val lineData = reports.mapIndexed { index: Int, reportModel: ReportModel ->
                if (reportModel.rating == null && (index == 0 || index == reports.size - 1)) {
                    reportModel.copy(rating = 2.5f)
                } else {
                    reportModel
                }
            }.filter {
                it.rating != null
            }.map {
                ValueDataEntry(formatDate(groupBy, it.collectedTime), it.rating)
            }

            val max = reports.map { it.value }.max() ?: 0f
            val min = reports.map { it.value }.min() ?: 0f

            cartesian.column(columnData).name(type.toString())
                .fill(
                    "function() {" +
                            "var max = ${max};\n" +
                            "if (this.value > max*4/5 + max/5) return '#f56566';\n" +
                            "if (this.value > max*3/5 + max/5) return 'orange';\n" +
                            "if (this.value > max*2/5 + max/5) return 'yellow';\n" +
                            "return '#7dc206';}"
                )
                .stroke("#ffffff", 0, "5", StrokeLineJoin.BEVEL, StrokeLineCap.SQUARE)
                .legendItem().enabled(false)

            val scalesLinear = Linear.instantiate()
            scalesLinear.minimum(1.0f)
            scalesLinear.maximum(5.0f)
            scalesLinear.ticks("{ interval: 1 }")

            val extraYAxis = cartesian.yAxis(1)
            extraYAxis.orientation(Orientation.RIGHT)
                .scale(scalesLinear)

            val line = cartesian.line(lineData).stroke("{thickness: 2, color: '#0091fc'}")
            line.markers().enabled(true).type(MarkerType.CIRCLE)
            line.name("Average Rating")
            line.yScale(scalesLinear)
            line.connectMissingPoints(true)

            cartesian.yScale().minimum(minOf(min, 0f))

            cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: } $unit")

            cartesian.xAxis(0).title("Timeline")
            cartesian.xZoom().setToPointsCount(7, true, null)
        }
        return super.onStateChanged(viewState)
    }

    private fun formatDate(groupBy: GroupType?, calendar: Calendar): String {
        return when (groupBy) {
            GroupType.MONTHLY -> shortDateFormat.format(calendar.time)
            GroupType.WEEKLY -> "W${weekFormat.format(calendar.time)}"
            else -> fullDateFormat.format(calendar.time)
        }
    }
}
