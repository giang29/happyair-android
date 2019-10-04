package toptal.test.project.remote

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.CallAdapter
import retrofit2.converter.moshi.MoshiConverterFactory
import toptal.test.project.common.TAG_BOOLEAN_DEBUG
import toptal.test.project.remote.common.AuthenticationInterceptor
import toptal.test.project.remote.common.AQRxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

internal const val TAG_OKHTTPCLIENT_AUTHENTICATION = "OKHTTPCLIENT_AUTHENTICATION"
internal const val TAG_INTERCEPTOR_LOGGING = "INTERCEPTOR_LOGGING"
internal const val TAG_INTERCEPTOR_AUTHENTICATION = "INTERCEPTOR_AUTHENTICATION"
internal const val TAG_OKHTTPCLIENT_DEFAULT = "OKHTTPCLIENT_DEFAULT"

val remoteModule = Kodein.Module("RemoteModule") {

    bind<OkHttpClient>(tag = TAG_OKHTTPCLIENT_AUTHENTICATION) with singleton {
        OkHttpClient.Builder()
            .addInterceptor(instance(tag = TAG_INTERCEPTOR_LOGGING))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    bind<OkHttpClient>(tag = TAG_OKHTTPCLIENT_DEFAULT) with singleton {
        OkHttpClient.Builder()
            .authenticator(instance())
            .addInterceptor(instance(tag = TAG_INTERCEPTOR_AUTHENTICATION))
            .addInterceptor(instance(tag = TAG_INTERCEPTOR_LOGGING))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    bind<Interceptor>(tag = TAG_INTERCEPTOR_LOGGING) with singleton {
        val isDebug: Boolean = instance(tag = TAG_BOOLEAN_DEBUG)
        HttpLoggingInterceptor().apply {
            level =
                if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    bind<Interceptor>(tag = TAG_INTERCEPTOR_AUTHENTICATION) with singleton {
        AuthenticationInterceptor(instance())
    }

    bind<MoshiConverterFactory>() with singleton {
        MoshiConverterFactory.create(instance())
    }

    /**
     * Bind [Moshi] instance
     */
    bind<Moshi>() with singleton {
        Moshi.Builder().build()
    }

    bind<CallAdapter.Factory>() with singleton {
        AQRxJava2CallAdapterFactory.create()
    }
}
