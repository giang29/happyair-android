package toptal.test.project.meal.common

import android.content.Context
import android.content.SharedPreferences
import toptal.test.project.common.SharedPreferencesHelper

private const val PREF_NAME_PACKAGE = "toptal.test.project.meal"

private const val PREF_KEY_TOKEN_EXPIRATION = "TOKEN_EXPIRATION"
private const val PREF_KEY_REFRESH_TOKEN = "REFRESH_TOKEN"
private const val PREF_KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
private const val PREF_KEY_STAY_LOG_IN = "STAY_LOG_IN"

class SharedPreferencesHelperImpl(context: Context): SharedPreferencesHelper {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME_PACKAGE, Context.MODE_PRIVATE)

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    override var accessToken: String
        get() = sharedPreferences.getString(PREF_KEY_ACCESS_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PREF_KEY_ACCESS_TOKEN, value).apply()

    override var refreshToken: String
        get() = sharedPreferences.getString(PREF_KEY_REFRESH_TOKEN, "") ?: ""
        set(value) = sharedPreferences.edit().putString(PREF_KEY_REFRESH_TOKEN, value).apply()

    override var tokenExpiresIn: Long
        get() = sharedPreferences.getLong(PREF_KEY_TOKEN_EXPIRATION, 0)
        set(value) = sharedPreferences.edit().putLong(PREF_KEY_TOKEN_EXPIRATION, value).apply()

    override var stayLogIn: Boolean
        get() = sharedPreferences.getBoolean(PREF_KEY_STAY_LOG_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(PREF_KEY_STAY_LOG_IN, value).apply()
}
