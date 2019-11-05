package toptal.test.project.domain

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import toptal.test.project.common.TAG_SCHEDULER_POST_EXECTION
import toptal.test.project.domain.feedback.FetchAllFeedbackUseCase
import toptal.test.project.domain.feedback.FetchAllFeedbackUseCaseImpl
import toptal.test.project.domain.report.FetchReportForRoomUseCase
import toptal.test.project.domain.report.FetchReportForRoomUseCaseImpl
import toptal.test.project.domain.room.FetchRoomUseCase
import toptal.test.project.domain.room.FetchRoomUseCaseImpl

private const val TAG_SCHEDULER_BACKGROUND = "SCHEDULER_BACKGROUND"
val domainModule: Kodein.Module = Kodein.Module("DomainModule") {

    bind<Scheduler>(tag = TAG_SCHEDULER_BACKGROUND) with provider {
        Schedulers.io()
    }

    bind<FetchAllFeedbackUseCase>() with provider {
        FetchAllFeedbackUseCaseImpl(
            instance(),
            instance(tag = TAG_SCHEDULER_BACKGROUND),
            instance(tag = TAG_SCHEDULER_POST_EXECTION)
        )
    }

    bind<FetchReportForRoomUseCase>() with provider {
        FetchReportForRoomUseCaseImpl(
            instance(),
            instance(tag = TAG_SCHEDULER_BACKGROUND),
            instance(tag = TAG_SCHEDULER_POST_EXECTION)
        )
    }

    bind<FetchRoomUseCase>() with provider {
        FetchRoomUseCaseImpl(
            instance(),
            instance(tag = TAG_SCHEDULER_BACKGROUND),
            instance(tag = TAG_SCHEDULER_POST_EXECTION)
        )
    }
}
