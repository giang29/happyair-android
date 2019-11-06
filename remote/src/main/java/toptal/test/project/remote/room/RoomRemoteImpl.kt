package toptal.test.project.remote.room

import io.reactivex.Single
import toptal.test.project.common.model.RoomModel
import toptal.test.project.data.room.RoomRemote
import toptal.test.project.remote.HappyAirGateway

internal class RoomRemoteImpl(private val happyAirGateway: HappyAirGateway): RoomRemote {

    override fun getRooms(): Single<List<RoomModel>> {
        return happyAirGateway.getRooms()
    }
}
