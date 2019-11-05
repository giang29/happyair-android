package toptal.test.project.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import toptal.test.project.remote.feedback.RatingsRemoteResponse
import toptal.test.project.remote.room.RoomsRemoteResponse

interface HappyAirGateway {
    @GET("rooms")
    fun getRooms(
        @Query("name") name: String = "Kaisaniemen"
    ) : Single<RoomsRemoteResponse>

    @GET("feedbacks")
    fun getFeedbacks(
        @Query("room") room: String,
        @Query("limitPoint") limit: Float
    ) : Single<RatingsRemoteResponse>
}
