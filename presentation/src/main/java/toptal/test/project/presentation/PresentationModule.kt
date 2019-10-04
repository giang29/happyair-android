package toptal.test.project.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

private inline fun <reified T : ViewModel> Kodein.Builder.bindViewModel(
    overrides: Boolean? = null
): Kodein.Builder.TypeBinder<T> {
    return bind<T>(T::class.java.simpleName, overrides)
}

val presentationModule: Kodein.Module = Kodein.Module("PresentationModule") {

    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(kodein.direct)
    }
}
