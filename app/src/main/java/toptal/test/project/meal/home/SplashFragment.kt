package toptal.test.project.meal.home


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

import toptal.test.project.meal.R
import toptal.test.project.meal.base.StatelessBaseFragment
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
internal class SplashFragment : StatelessBaseFragment() {
    override val layoutResource: Int = R.layout.f_splash

    private lateinit var disposable: Disposable
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable = Observable.just(Unit)
            .delay(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                findNavController().navigate(R.id.action_f_splash_to_f_home)
            }
    }

    override fun onDestroyView() {
        if(!disposable.isDisposed) disposable.dispose()
        super.onDestroyView()
    }
}
