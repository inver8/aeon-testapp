package ru.aeon.testapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.aeon.testapp.data.base.Either
import ru.aeon.testapp.domain.error.NetworkError
import ru.aeon.testapp.domain.model.ApiToken

interface AuthRepository {
    
    fun requestLoginUsernamePassword(username: String, password: String): Flow<Either<NetworkError, ApiToken>>
    
}