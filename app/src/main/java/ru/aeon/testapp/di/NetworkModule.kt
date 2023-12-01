package ru.aeon.testapp.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.aeon.testapp.BuildConfig
import ru.aeon.testapp.data.remote.client.interceptor.AuthInterceptor
import ru.aeon.testapp.data.remote.client.interceptor.UserAgentInterceptor
import ru.aeon.testapp.data.remote.service.ApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )
    }
    
    @Singleton
    @Provides
    fun provideApiHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        userAgentInterceptor: UserAgentInterceptor,
        authInterceptor: AuthInterceptor
    ) = OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(userAgentInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    
    @Singleton
    @Provides
    fun provideApiService(
        httpClient: OkHttpClient,
        gson: Gson
    ): ApiService {
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.BASE_API_URL)
            .build()
            .create(ApiService::class.java)
    }
    
}