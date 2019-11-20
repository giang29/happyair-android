package toptal.test.project.meal.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

internal abstract class StatelessBaseFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by lazy { (activity?.applicationContext as KodeinAware).kodein }

    protected abstract val layoutResource: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource, container, false)
    }
}
