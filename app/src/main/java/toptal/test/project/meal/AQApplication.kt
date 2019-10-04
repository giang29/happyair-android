package toptal.test.project.meal

import android.app.Application
import android.content.Context
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.with
import toptal.test.project.common.TAG_BOOLEAN_DEBUG
import toptal.test.project.exceptions.ClientError
import toptal.test.project.injection.dependencyGraph
import java.io.IOException
import java.net.SocketException

@Suppress("Unused")
class AQApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(applicationModule)
        import(dependencyGraph)

        constant(tag = TAG_BOOLEAN_DEBUG) with BuildConfig.DEBUG

        bind<Context>() with provider {
            this@AQApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        initRxJavaErrorHandler()
    }

    @Suppress("UnstableApiUsage")
    private fun initRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                when (e.cause) {
                    is ClientError -> {
                        // Swallow it for now
                        return@setErrorHandler
                    }
                }
            }
            if (e is IOException || e is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (e is OnErrorNotImplementedException) {
                // that's likely a bug in the application
                Thread.currentThread()
                    .uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            if (e is NullPointerException || e is IllegalArgumentException) {
                Thread.currentThread()
                    .uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread()
                    .uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
        }
    }
}
