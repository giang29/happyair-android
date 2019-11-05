package toptal.test.project.data.room

import io.reactivex.Completable
import io.reactivex.Maybe
import toptal.test.project.common.model.RoomModel

interface RoomCache {
    fun saveRooms(rooms: List<RoomModel>): Completable

    fun getRooms(): Maybe<List<RoomModel>>
}
