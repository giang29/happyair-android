package toptal.test.project.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoomPresentationModel(
    val id: String,
    val name: String,
    val co2: Int?,
    val humidity: Int?,
    val pm1: Int?,
    val pm10: Int?,
    val pm2_5: Int?,
    val temperature: Int?,
    val tvoc: Int?,
    val pressureDiff: Int?
): Parcelable
