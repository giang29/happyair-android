package toptal.test.project.meal.home

import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import kotlinx.android.synthetic.main.f_home.*
import toptal.test.project.meal.R
import toptal.test.project.meal.base.StatelessBaseFragment

internal class HomeFragment : StatelessBaseFragment() {
    override val layoutResource: Int = R.layout.f_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        f_home_navigation.menu.forEach {
            it.isEnabled = !it.isChecked
        }
        f_home_navigation.setOnNavigationItemSelectedListener { item ->
            f_home_navigation.menu.forEach {
                it.isEnabled = item.itemId != it.itemId
            }
            onNavDestinationSelected(
                item,
                findNavController(requireActivity(), R.id.f_home_nav_host)
            )
        }
    }
}
