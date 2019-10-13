package toptal.test.project.meal.home

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.f_home.*
import toptal.test.project.meal.R
import toptal.test.project.meal.base.StatelessBaseFragment

internal class HomeFragment : StatelessBaseFragment() {
    override val layoutResource: Int = R.layout.f_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        f_home_navigation.setupWithNavController(findNavController(requireActivity(), R.id.f_home_nav_host))
    }
}
