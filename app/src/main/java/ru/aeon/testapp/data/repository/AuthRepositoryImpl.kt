package ru.aeon.testapp.data.repository

import ru.aeon.testapp.data.base.BaseRepository
import ru.aeon.testapp.data.local.ApiTokenProvider
import ru.aeon.testapp.data.remote.dto.auth.LoginRequestDto
import ru.aeon.testapp.data.remote.service.ApiService
import ru.aeon.testapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val tokenProvider: ApiTokenProvider
) : BaseRepository(), AuthRepository {
    
    override fun requestLoginUsernamePassword(username: String, password: String) = doNetworkRequestWithMapping {
        api.requestAuthPhoneCode(LoginRequestDto(username, password)).onSuccess { 
            tokenProvider.setApiToken(it.response?.token) 
        }
    }
    
    override fun logout() {
        tokenProvider.clearApiToken()
    }
    
    override fun isLoggedIn() = tokenProvider.isTokenExist()
    
}