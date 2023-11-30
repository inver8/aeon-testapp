package ru.aeon.testapp.data.remote.client.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject internal constructor() : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}