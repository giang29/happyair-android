package toptal.test.project.meal.report


import android.os.Bundle
import toptal.test.project.meal.R
import toptal.test.project.meal.base.StatelessActivity

internal class HomeActivity : StatelessActivity() {
    override val layoutResource: Int = R.layout.a_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ReportFragment.newInstance())
            .commit()
    }
}
