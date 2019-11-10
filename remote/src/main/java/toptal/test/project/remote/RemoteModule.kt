package toptal.test.project.remote

import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import toptal.test.project.common.TAG_BOOLEAN_DEBUG
import toptal.test.project.data.feedback.FeedbackRemoteDataStore
import toptal.test.project.data.report.ReportRemoteDataStore
import toptal.test.project.data.room.RoomRemote
import toptal.test.project.remote.common.AQRxJava2CallAdapterFactory
import toptal.test.project.remote.feedback.FeedbackRemoteDataStoreImpl
import toptal.test.project.remote.report.ReportRemoteDataStoreImpl
import toptal.test.project.remote.room.RoomRemoteImpl
import java.util.concurrent.TimeUnit

internal const val TAG_INTERCEPTOR_LOGGING = "INTERCEPTOR_LOGGING"

val remoteModule = Kodein.Module("RemoteModule") {
    bind<HappyAirGateway>() with singleton {
        Retrofit.Builder()
            .addCallAdapterFactory(instance())
            .addConverterFactory(instance())
            .baseUrl("https://happyair.herokuapp.com/")
            .client(instance())
            .build()
            .create(HappyAirGateway::class.java)
    }

    bind<NuukaService>() with singleton {
        Retrofit.Builder()
            .addCallAdapterFactory(instance())
            .addConverterFactory(instance())
            .baseUrl("https://nuukacustomerwebapi.azurewebsites.net/api/v2.0/")
            .client(instance())
            .build()
            .create(NuukaService::class.java)
    }

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
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

    bind<FeedbackRemoteDataStore>() with provider {
        FeedbackRemoteDataStoreImpl(instance(), instance())
    }

    bind<ReportRemoteDataStore>() with provider {
        ReportRemoteDataStoreImpl()
    }

    bind<RoomRemote>() with provider {
        RoomRemoteImpl(instance())
    }
}
