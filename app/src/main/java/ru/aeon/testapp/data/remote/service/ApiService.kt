package ru.aeon.testapp.data.remote.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import ru.aeon.testapp.data.remote.dto.auth.LoginRequestDto

interface ApiService {
    
    @POST("/login")
    suspend fun requestAuthPhoneCode(@Body loginRequest: LoginRequestDto): Response<String>
}