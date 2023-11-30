package ru.aeon.testapp.domain.repository

import ru.aeon.testapp.domain.common.RemoteWrapper
import ru.aeon.testapp.domain.model.ApiToken

interface AuthRepository {
    
    fun requestLoginUsernamePassword(username: String, password: String): RemoteWrapper<ApiToken>
    fun logout()
    fun isLoggedIn(): Boolean
    
}