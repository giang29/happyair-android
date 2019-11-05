package toptal.test.project.data.room

import io.reactivex.Single
import toptal.test.project.common.model.RoomModel
import toptal.test.project.domain.room.RoomRepository

internal class RoomRepositoryImpl(
    private val roomCache: RoomCache,
    private val roomRemote: RoomRemote
): RoomRepository {
    override fun getRooms(): Single<List<RoomModel>> {
        val loadRoomsRemotely = Single.defer {
            roomRemote.getRooms()
                .flatMap {
                    roomCache.saveRooms(it)
                        .andThen(Single.just(it))
                }.onErrorResumeNext {
                    Single.just(emptyList())
                }
        }
        return roomCache.getRooms()
            .switchIfEmpty(loadRoomsRemotely)
            .onErrorResumeNext {
                loadRoomsRemotely
            }
    }
}
