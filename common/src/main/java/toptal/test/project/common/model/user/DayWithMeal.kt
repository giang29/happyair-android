package toptal.test.project.common.model.user

import toptal.test.project.common.toDisplayDate

data class DateWithMeal(
    val date: String,
    val meals: List<MealModel>,
    val overIntake: Boolean
) {
    fun getDateDisplay(): String {
        return date.toDisplayDate()
    }
}
