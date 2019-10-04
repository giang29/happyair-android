package toptal.test.project.injection

import org.kodein.di.Kodein
import toptal.test.project.cache.cacheModule
import toptal.test.project.data.dataModule
import toptal.test.project.domain.domainModule
import toptal.test.project.presentation.presentationModule
import toptal.test.project.remote.remoteModule

val dependencyGraph: Kodein.Module = Kodein.Module("DependencyGraph") {
    import(cacheModule)
    import(dataModule)
    import(domainModule)
    import(presentationModule)
    import(remoteModule)
}
