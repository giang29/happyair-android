package toptal.test.project.remote.common

import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

/**
 * Class until adapt retrofit answers until RxJava which convert all the errors before emiting on the
 * data stream until NetworkExceptions
 */
internal class AQRxJava2CallAdapterFactory private constructor() : CallAdapter.Factory() {

    private val rxJava2CallAdapterFactory: RxJava2CallAdapterFactory =
        RxJava2CallAdapterFactory.create()

    @Suppress("UNCHECKED_CAST")
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return AQRxJava2CallAdapter(
            rxJava2CallAdapterFactory.get(
                returnType,
                annotations,
                retrofit
            ) as CallAdapter<Any, Any>
        )
    }

    companion object {
        fun create(): CallAdapter.Factory = AQRxJava2CallAdapterFactory()
    }
}
