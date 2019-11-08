package toptal.test.project.meal.home

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.commitNow
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import kotlinx.android.synthetic.main.f_home.*
import toptal.test.project.meal.R
import toptal.test.project.meal.base.StatelessBaseFragment
import toptal.test.project.meal.feedback.FeedbackFragment
import toptal.test.project.meal.report.ReportFragment

internal class HomeFragment : StatelessBaseFragment() {
    override val layoutResource: Int = R.layout.f_home

    private var selectedItemId: Int = R.id.m_home_navigation_report

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        f_home_navigation.setOnNavigationItemSelectedListener { item ->

            navigate(item.itemId)
            true
        }
        f_home_navigation.selectedItemId = selectedItemId
        navigate(f_home_navigation.selectedItemId)
    }

    private fun navigate(@IdRes menuItemId: Int) {
        selectedItemId = menuItemId
        val reportFragment = childFragmentManager.findFragmentByTag("report")
        val feedbackFragment = childFragmentManager.findFragmentByTag("feedback")
        when(menuItemId) {
            R.id.m_home_navigation_report -> {

                childFragmentManager.commitNow(allowStateLoss = true) {
                    reportFragment?.run(::show)
                        ?: run { add(R.id.f_home_container, ReportFragment(), "report") }
                    feedbackFragment?.run(::hide)
                }
            }
            R.id.m_home_navigation_ratings -> {

                childFragmentManager.commitNow(allowStateLoss = true) {
                    feedbackFragment?.run(::show)
                        ?: run { add(R.id.f_home_container, FeedbackFragment(), "feedback") }
                    reportFragment?.run(::hide)
                }
            }
        }
    }
}
