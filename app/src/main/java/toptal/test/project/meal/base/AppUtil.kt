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

fun Activity.hideKeyboard() {
    window?.decorView?.windowToken?.run {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(this, 0)
    }
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

fun View.clicksThrottle(maxDelay: Long = DEFAULT_CLICK_THROTTLE_PERIOD): Observable<Unit> {
    return clicks()
        .throttleFirst(maxDelay, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
}
