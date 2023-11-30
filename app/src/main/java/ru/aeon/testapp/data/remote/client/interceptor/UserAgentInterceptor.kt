package ru.aeon.testapp.data.remote.client.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.aeon.testapp.BuildConfig
import javax.inject.Inject

class UserAgentInterceptor @Inject internal constructor() : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .header("User-Agent", BuildConfig.USER_AGENT)
                .header("app-key", BuildConfig.API_APP_KEY)
                .header("v", BuildConfig.API_V)
                .build()
        )
    }
}