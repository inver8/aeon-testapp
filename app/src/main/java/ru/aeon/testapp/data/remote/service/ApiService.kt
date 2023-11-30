package ru.aeon.testapp.data.remote.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.aeon.testapp.data.remote.client.interceptor.AuthRequired
import ru.aeon.testapp.data.remote.dto.auth.LoginRequestDto
import ru.aeon.testapp.data.remote.dto.auth.LoginResponseDto
import ru.aeon.testapp.data.remote.dto.base.ResponseDto
import ru.aeon.testapp.data.remote.dto.payments.PaymentDto

interface ApiService {
    
    @POST("login")
    suspend fun requestAuthPhoneCode(@Body loginRequest: LoginRequestDto): Response<ResponseDto<LoginResponseDto>>
    
    @AuthRequired
    @GET("payments")
    suspend fun getPayments(): Response<ResponseDto<List<PaymentDto>>>
}