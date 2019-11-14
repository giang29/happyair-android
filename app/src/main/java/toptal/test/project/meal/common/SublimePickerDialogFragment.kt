package toptal.test.project.meal.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.appeaser.sublimepickerlibrary.SublimePicker
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker
import java.util.*

private const val START_DATE = "START_DATE"
private const val END_DATE = "END_DATE"

internal class SublimePickerDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val listener = object : SublimeListenerAdapter() {

            override fun onCancelled() {
                dismiss()
            }

            override fun onDateTimeRecurrenceSet(
                sublimeMaterialPicker: SublimePicker?,
                selectedDate: SelectedDate?,
                hourOfDay: Int,
                minute: Int,
                recurrenceOption: SublimeRecurrencePicker.RecurrenceOption?,
                recurrenceRule: String?
            ) {
                (parentFragment as? DateRangePickedListener)?.onDateRangePicked(
                    selectedDate!!.startDate,
                    selectedDate.endDate
                )
                dismiss()
            }
        }

        val sublimePicker = SublimePicker(context)
        val sublimeOptions = SublimeOptions()
        sublimeOptions.pickerToShow = SublimeOptions.Picker.DATE_PICKER
        sublimeOptions.setDisplayOptions(SublimeOptions.ACTIVATE_DATE_PICKER)
        sublimeOptions.setCanPickDateRange(true)
        sublimeOptions.setDateParams(
            SelectedDate(
                Calendar.getInstance().apply { timeInMillis = arguments!!.getLong(START_DATE) },
                Calendar.getInstance().apply { timeInMillis = arguments!!.getLong(END_DATE) }
            )
        )
        sublimePicker.initializePicker(sublimeOptions, listener)
        return sublimePicker
    }

    companion object {
        fun newInstance(startDate: Long, endDate: Long) = SublimePickerDialogFragment()
            .apply {
                arguments = Bundle().apply {
                    putLong(START_DATE, startDate)
                    putLong(END_DATE, endDate)
                }
            }
    }

    interface DateRangePickedListener {
        fun onDateRangePicked(startDate: Calendar, endDate: Calendar)
    }
}
