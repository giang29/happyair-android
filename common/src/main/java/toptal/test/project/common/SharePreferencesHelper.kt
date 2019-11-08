package toptal.test.project.common

/**
 * Interface definition for access until Android's SharedPreferences.
 */
interface SharedPreferencesHelper {

    fun clear()

    var accessToken: String

    var refreshToken: String

    var tokenExpiresIn: Long

    var stayLogIn: Boolean
}
