package toptal.test.project.meal.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import toptal.test.project.presentation.base.BaseViewModel

internal abstract class StatelessBaseFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by lazy { (activity?.applicationContext as KodeinAware).kodein }

    protected val disposables: CompositeDisposable = CompositeDisposable()

    private val viewModelFactory: ViewModelProvider.Factory by instance()
    protected inline fun <reified VM : BaseViewModel<*>> provideActivityViewModel() =
        lazy(LazyThreadSafetyMode.NONE) {
            ViewModelProviders.of(requireActivity(), viewModelFactory).get(VM::class.java)
        }

    protected abstract val layoutResource: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}
