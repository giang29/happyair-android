package toptal.test.project.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import toptal.test.project.remote.feedback.DataValueRemoteModel

interface NuukaService {

    @GET("GetMeasurementDataByIDs")
    fun getCollectedData(
        @Query("DataPointIDs")
        dataPointIds: String,
        @Query("StartTime")
        startTime: String,
        @Query("EndTime")
        endTime: String,
        @Query("Building")
        building: String = "2410",
        @Query("\$token")
        token: String = "L2FyTzA3UHp1cGdnUzNMcjRuSUIvZ2o0Q2tCclhQam44SGo5Nm9HcE0zcz06TWV0cm9wb2xpYV9BUEk6NjM3MDMxOTIzMzk5NjcxNzEwOlRydWU=",
        @Query("\$format")
        format: String = "json",
        @Query("MeasurementSystem")
        measurementSystem: String = "SI"
    ): Single<List<DataValueRemoteModel>>
}
