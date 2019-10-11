package toptal.test.project.meal.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import toptal.test.project.presentation.base.BaseViewModel
import toptal.test.project.presentation.base.BaseViewState

internal abstract class BaseDialogFragment<VM : BaseViewModel<VS>, VS : BaseViewState> :
    DialogFragment(),
    KodeinAware {

    override val kodein: Kodein by lazy { (activity?.applicationContext as KodeinAware).kodein }

    private val viewModelFactory: ViewModelProvider.Factory by instance()
    protected inline fun <reified VM : BaseViewModel<VS>> provideViewModel() =
        lazy(LazyThreadSafetyMode.NONE) {
            ViewModelProviders.of(this, viewModelFactory).get(VM::class.java)
        }

    protected abstract val viewModel: VM

    protected abstract val layoutResource: Int

    private lateinit var dialogView: View

    abstract fun onStateChanged(viewState: VS)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialogView = LayoutInflater.from(requireContext()).inflate(layoutResource, null, false)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        return builder.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return dialogView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewStates.observe(viewLifecycleOwner, Observer { viewState ->
            onStateChanged(viewState)
        })
    }
}
