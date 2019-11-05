package toptal.test.project.domain.room

import io.reactivex.Scheduler
import io.reactivex.Single
import toptal.test.project.common.model.RoomModel

interface FetchRoomUseCase {
    fun execute(): Single<List<RoomModel>>
}

internal class FetchRoomUseCaseImpl(
    private val roomRepository: RoomRepository,
    private val executionThread: Scheduler,
    private val postExecutionThread: Scheduler
) : FetchRoomUseCase {
    override fun execute(): Single<List<RoomModel>> {
        return roomRepository.getRooms()
            .subscribeOn(executionThread)
            .observeOn(postExecutionThread)
    }
}
