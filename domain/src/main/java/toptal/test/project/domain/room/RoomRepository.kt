package toptal.test.project.domain.room

import io.reactivex.Single
import toptal.test.project.common.model.RoomModel

interface RoomRepository {
    fun getRooms(): Single<List<RoomModel>>
}
