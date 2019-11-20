package toptal.test.project.meal.base

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

internal const val DEFAULT_CLICK_THROTTLE_PERIOD = 250L

fun View.clicksThrottle(maxDelay: Long = DEFAULT_CLICK_THROTTLE_PERIOD): Observable<Unit> {
    return clicks()
        .throttleFirst(maxDelay, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
}
