package toptal.test.project.meal.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState

internal abstract class BaseFragment<VM : BaseViewModel<VS>, VS : BaseViewState>
    : Fragment(), KodeinAware {

    override val kodein: Kodein by lazy { (activity?.applicationContext as KodeinAware).kodein }

    protected var isInitialized = false
        private set

    private val viewModelFactory: ViewModelProvider.Factory by instance()
    protected inline fun <reified VM : BaseViewModel<VS>> provideViewModel() =
        lazy(LazyThreadSafetyMode.NONE) {
            ViewModelProviders.of(this, viewModelFactory).get(VM::class.java)
        }

    protected inline fun <reified VM : BaseViewModel<*>> provideActivityViewModel() =
        lazy(LazyThreadSafetyMode.NONE) {
            ViewModelProviders.of(requireActivity(), viewModelFactory).get(VM::class.java)
        }

    protected abstract val viewModel: VM

    protected abstract val layoutResource: Int

    @CallSuper
    protected open fun onStateChanged(viewState: VS): Boolean {
        isInitialized = true
        return isInitialized
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource, container, false)
    }

    override fun onDestroyView() {
        isInitialized = false
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewStates.observe(viewLifecycleOwner, Observer { viewState ->
            onStateChanged(viewState)
        })
    }
}
