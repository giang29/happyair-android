package toptal.test.project.meal.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import toptal.test.project.common.SharedPreferencesHelper

internal abstract class StatelessActivity  : AppCompatActivity(), KodeinAware {

    protected abstract val layoutResource: Int

    protected val sharedPreferencesHelper: SharedPreferencesHelper by instance()

    override val kodein: Kodein by lazy { (applicationContext as KodeinAware).kodein }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        //Exclude some Views to prevent flashing during transition animations:
        window.enterTransition?.apply {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        window.exitTransition?.apply {
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
    }
}
