package toptal.test.project.remote.common

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import toptal.test.project.common.SharedPreferencesHelper

/**
 * [Interceptor] to refresh access token.
 */
internal const val HEADER_AUTHORISATION = "Authorization"
internal class AuthenticationInterceptor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Get original request
        val request: Request = chain.request()

        // Return original request if there's no acces token present
        if (sharedPreferencesHelper.accessToken.isBlank()) return chain.proceed(request)

        // Build new request
        val requestBuilder: Request.Builder = request.newBuilder()

        // Add authorisation header
        requestBuilder.header(
            HEADER_AUTHORISATION,
            "Bearer ${sharedPreferencesHelper.accessToken}"
        )
        val newRequest: Request = requestBuilder.build()

        // Send request
        return chain.proceed(newRequest)
    }

}
