package ru.aeon.testapp.data.remote.dto.auth

import com.google.gson.annotations.SerializedName
import ru.aeon.testapp.data.util.DataMapper
import ru.aeon.testapp.domain.model.ApiToken

data class LoginResponseDto(
    
    @SerializedName("token")
    val token: String

) : DataMapper<ApiToken> {
    
    override fun mapToDomain(): ApiToken {
        return ApiToken(token)
    }
}