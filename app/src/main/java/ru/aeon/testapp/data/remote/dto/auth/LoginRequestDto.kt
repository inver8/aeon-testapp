package ru.aeon.testapp.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    
    @SerializedName("login")
    val login: String,
    
    @SerializedName("password")
    val password: String
    
)