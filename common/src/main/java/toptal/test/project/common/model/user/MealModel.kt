package toptal.test.project.common.model.user

import java.util.*

data class MealModel(
    val title: String,
    val date: String,
    val time: String,
    val calories: Int,
    val id: String
): Comparable<MealModel> {
    private fun getCalendar(): Calendar {
        val dateSplit = date.split("/")
        val timeSplit = time.split(":")
        return Calendar.getInstance()
            .apply {
                set(Calendar.YEAR, dateSplit[0].toInt())
                set(Calendar.MONTH, dateSplit[1].toInt())
                set(Calendar.DAY_OF_MONTH, dateSplit[2].toInt())
                set(Calendar.HOUR_OF_DAY, timeSplit[0].toInt())
                set(Calendar.MINUTE, timeSplit[1].toInt())
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
    }
    override fun compareTo(other: MealModel): Int {
        return getCalendar().compareTo(other.getCalendar())
    }
}
