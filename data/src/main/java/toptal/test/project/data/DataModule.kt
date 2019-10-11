package toptal.test.project.data

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import toptal.test.project.data.feedback.FeedbackRepositoryImpl
import toptal.test.project.data.report.ReportRepositoryImpl
import toptal.test.project.domain.feedback.FeedbackRepository
import toptal.test.project.domain.report.ReportRepository

val dataModule: Kodein.Module = Kodein.Module("DataModule") {
    bind<FeedbackRepository>() with provider {
        FeedbackRepositoryImpl(instance())
    }
    bind<ReportRepository>() with provider {
        ReportRepositoryImpl(instance())
    }
}
