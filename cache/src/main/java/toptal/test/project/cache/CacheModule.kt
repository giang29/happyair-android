package toptal.test.project.cache

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import toptal.test.project.cache.room.RoomCacheImpl
import toptal.test.project.data.room.RoomCache

val cacheModule: Kodein.Module = Kodein.Module("CacheModule") {
    bind<RoomCache>() with singleton {
        RoomCacheImpl
    }
}
