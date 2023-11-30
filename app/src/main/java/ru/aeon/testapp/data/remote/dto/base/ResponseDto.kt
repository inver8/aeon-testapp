package ru.aeon.testapp.data.remote.dto.base

import com.google.gson.annotations.SerializedName

data class ResponseDto<T>(
    
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("response")
    val response: T?,
    
    @SerializedName("error")
    val error: ApiErrorDto?
    
) 