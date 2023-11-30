package ru.aeon.testapp.data.repository

import ru.aeon.testapp.data.base.BaseRepository
import ru.aeon.testapp.data.local.ApiTokenProvider
import ru.aeon.testapp.data.remote.dto.auth.LoginRequestDto
import ru.aeon.testapp.data.remote.service.ApiService
import ru.aeon.testapp.domain.common.Either
import ru.aeon.testapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val tokenProvider: ApiTokenProvider
) : BaseRepository(), AuthRepository {
    
    override fun requestLoginUsernamePassword(username: String, password: String) = doNetworkRequest(
        request = {
            api.requestAuthPhoneCode(LoginRequestDto(username, password))
                .onSuccess { r -> tokenProvider.setApiToken(r.response?.token) }
        },
        successful = { body -> Either.Right(body.mapToDomain()) }
    )
    
    override fun logout() {
        tokenProvider.clearApiToken()
    }
    
    override fun isLoggedIn() = tokenProvider.isTokenExist()
    
}