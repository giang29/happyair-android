package toptal.test.project.domain

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

private const val TAG_SCHEDULER_BACKGROUND = "SCHEDULER_BACKGROUND"
val domainModule: Kodein.Module = Kodein.Module("DomainModule") {

    bind<Scheduler>(tag = TAG_SCHEDULER_BACKGROUND) with provider {
        Schedulers.io()
    }
}
