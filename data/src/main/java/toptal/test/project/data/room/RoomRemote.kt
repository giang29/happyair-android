package toptal.test.project.data.room

import io.reactivex.Single
import toptal.test.project.common.model.RoomModel

interface RoomRemote {

    fun getRooms(): Single<List<RoomModel>>
}
