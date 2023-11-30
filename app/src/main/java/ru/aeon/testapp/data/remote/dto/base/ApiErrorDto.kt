package ru.aeon.testapp.data.remote.dto.base

import com.google.gson.annotations.SerializedName

data class ApiErrorDto(
    
    @SerializedName("error_code")
    val errorCode: String,
    
    @SerializedName("error_msg")
    val errorMessage: String
    
)