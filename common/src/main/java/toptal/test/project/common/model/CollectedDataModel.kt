package toptal.test.project.common.model

data class CollectedDataModel(
    val name: String,
    val value: Float,
    val type: AirDataType
) {

    fun getRating(): Rating {
        return when(type) {
            AirDataType.CO2-> when (value) {
                in 0f..350f -> Rating.VERY_GOOD
                in 350f..1000f -> Rating.GOOD
                in 1000f..2000f -> Rating.OK
                in 2000f..5000f -> Rating.BAD
                else -> Rating.TOO_BAD
            }
            AirDataType.TEMPERATURE -> when (value) {
                in 22f..25f -> Rating.VERY_GOOD
                in 22f*0.95f..25f*1.05f -> Rating.GOOD
                in 22f*0.85f..25f*1.15f -> Rating.OK
                in 22f*0.75f..25f*1.25f -> Rating.BAD
                else -> Rating.TOO_BAD
            }
            AirDataType.HUMIDITY -> when (value) {
                in 30f..50f -> Rating.VERY_GOOD
                in 27f..55f -> Rating.GOOD
                in 25f..60f -> Rating.OK
                in 20f..70f -> Rating.BAD
                else -> Rating.TOO_BAD
            }
            AirDataType.PM1 -> Rating.GOOD
            AirDataType.PM10 -> when (value) {
                in 0f..40f -> Rating.GOOD
                else -> Rating.BAD
            }
            AirDataType.PM2_5 -> when (value) {
                in 0f..25f -> Rating.GOOD
                else -> Rating.BAD
            }
            AirDataType.TVOC -> when (value) {
                in 0f..300f -> Rating.VERY_GOOD
                in 300f..1000f -> Rating.GOOD
                in 1000f..3000f -> Rating.OK
                in 3000f..10000f -> Rating.BAD
                else -> Rating.TOO_BAD
            }
            AirDataType.PRESSURE_DIFF -> Rating.GOOD
            else -> Rating.GOOD
        }
    }
}

