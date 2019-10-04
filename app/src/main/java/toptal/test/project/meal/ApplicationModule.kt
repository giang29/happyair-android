package toptal.test.project.meal

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.*
import toptal.test.project.common.SharedPreferencesHelper
import toptal.test.project.common.TAG_SCHEDULER_POST_EXECTION
import toptal.test.project.common.TAG_STRING_BASE_URL
import toptal.test.project.meal.common.SharedPreferencesHelperImpl


val applicationModule: Kodein.Module = Kodein.Module("ApplicationModule") {

    bind<Scheduler>(tag = TAG_SCHEDULER_POST_EXECTION) with provider {
        AndroidSchedulers.mainThread()
    }

    bind<SharedPreferencesHelper>() with singleton {
        SharedPreferencesHelperImpl(instance())
    }

    constant(tag = TAG_STRING_BASE_URL) with "https://meal-management-api.herokuapp.com/"
}
