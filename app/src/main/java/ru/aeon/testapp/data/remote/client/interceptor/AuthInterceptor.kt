package ru.aeon.testapp.data.remote.client.interceptor

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Invocation
import ru.aeon.testapp.data.local.ApiTokenProvider
import javax.inject.Inject

class AuthInterceptor @Inject internal constructor(
    private val tokenProvider: ApiTokenProvider
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        if (isAuthRequired(request)) {
            val apiToken = tokenProvider.getApiToken()
            
            if (apiToken.isNullOrBlank())
                return noAccessTokenResponse(request)
                
            val authRequest = request.newBuilder()
                .addHeader(AUTH_HEADER, apiToken)
                .build()
            
            return chain.proceed(authRequest)
        }
    
        return chain.proceed(request)
    }
    
    private fun isAuthRequired(request: Request): Boolean {
        val tag: Invocation? = request.tag(Invocation::class.java)
        return tag?.method()?.getAnnotation(AuthRequired::class.java) != null
    }
    
    private fun noAccessTokenResponse(request: Request) = Response.Builder()
        .request(request)
        .body("".toResponseBody(null)) 
        .protocol(Protocol.HTTP_2)
        .message("No access token")
        .code(401)
        .build()
    
    companion object {
        private const val AUTH_HEADER = "token"
    }
}