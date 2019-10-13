package toptal.test.project.meal.home

import androidx.navigation.findNavController
import toptal.test.project.meal.R
import toptal.test.project.meal.base.StatelessActivity

internal class HomeActivity : StatelessActivity() {
    override val layoutResource: Int = R.layout.a_home

    override fun onSupportNavigateUp() =
        findNavController(R.id.a_home_nav_host).navigateUp()
}
