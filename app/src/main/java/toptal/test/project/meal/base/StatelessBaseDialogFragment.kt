package toptal.test.project.meal.base

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import toptal.test.project.presentation.base.BaseViewModel

internal abstract class StatelessBaseDialogFragment
    : DialogFragment(), KodeinAware {

    protected val disposables = CompositeDisposable()

    override val kodein: Kodein by lazy { (activity?.applicationContext as KodeinAware).kodein }

    protected abstract val layoutResource: Int

    private val viewModelFactory: ViewModelProvider.Factory by instance()

    protected inline fun <reified VM : BaseViewModel<*>> provideParentFragmentViewModel() =
        lazy(LazyThreadSafetyMode.NONE) {
            ViewModelProviders.of(parentFragment!!, viewModelFactory).get(VM::class.java)
        }

    var onDismissListener: DialogInterface.OnDismissListener? = null

    private lateinit var dialogView: View

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

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        onDismissListener?.run {
            onDismiss(dialog)
        }
    }

}
