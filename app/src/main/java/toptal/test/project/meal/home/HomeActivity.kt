package toptal.test.project.meal.home

import androidx.navigation.findNavController
import toptal.test.project.meal.R
import toptal.test.project.meal.base.StatelessActivity
import toptal.test.project.presentation.model.RoomPresentationModel
import toptal.test.project.presentation.report.FeedbackPresentationModel

internal class HomeActivity : StatelessActivity() {
    override val layoutResource: Int = R.layout.a_home

    override fun onSupportNavigateUp() =
        findNavController(R.id.a_home_nav_host).navigateUp()


    fun onNavigateToFeedbackDetail(feedback: FeedbackPresentationModel, room: RoomPresentationModel) {
        findNavController(R.id.a_home_nav_host).navigate(
            HomeFragmentDirections.actionFHomeToFeedbackDetailFragment(feedback, room)
        )
    }
}
