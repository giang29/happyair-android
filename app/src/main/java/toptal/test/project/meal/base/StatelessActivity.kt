package toptal.test.project.meal.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

internal abstract class StatelessActivity  : AppCompatActivity(), KodeinAware {

    protected abstract val layoutResource: Int

    override val kodein: Kodein by lazy { (applicationContext as KodeinAware).kodein }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResource)
        //Exclude some Views until prevent flashing during transition animations:
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
