package toptal.test.project.meal.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import toptal.test.project.common.SharedPreferencesHelper
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState

internal abstract class BaseActivity<VM : BaseViewModel<VS>, VS : BaseViewState>
    : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by lazy { (applicationContext as KodeinAware).kodein }

    protected val sharedPreferencesHelper: SharedPreferencesHelper by instance()

    private val viewModelFactory: ViewModelProvider.Factory by instance()

    protected inline fun <reified VM : BaseViewModel<VS>> provideViewModel() =
        lazy(LazyThreadSafetyMode.NONE) {
            ViewModelProviders.of(this, viewModelFactory).get(VM::class.java)
        }

    protected abstract val viewModel: VM

    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        viewModel.viewStates.observe(this, Observer { viewState ->
            onStateChanged(viewState)
        })
        //Exclude some Views to prevent flashing during transition animations:
        window.enterTransition?.apply {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.exitTransition?.apply {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun onStateChanged(viewState: VS)
}

