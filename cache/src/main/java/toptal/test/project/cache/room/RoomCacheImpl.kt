package toptal.test.project.cache.room

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.rxkotlin.toCompletable
import toptal.test.project.common.model.RoomModel
import toptal.test.project.data.room.RoomCache

internal object RoomCacheImpl : RoomCache {

    private val roomRelay = BehaviorRelay.createDefault<Optional<List<RoomModel>>>(None)
    override fun saveRooms(rooms: List<RoomModel>): Completable {
        return {
            roomRelay.accept(Some(rooms))
        }.toCompletable()
    }

    override fun getRooms(): Maybe<List<RoomModel>> {
        return roomRelay.firstElement()
            .flatMap {
                if (it is Some)
                    Maybe.just(it.value)
                else {
                    Maybe.empty()
                }
            }
    }
}
