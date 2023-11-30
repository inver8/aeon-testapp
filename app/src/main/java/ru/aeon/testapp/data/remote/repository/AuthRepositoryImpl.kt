package ru.aeon.testapp.data.remote.repository

import kotlinx.coroutines.flow.Flow
import ru.aeon.testapp.data.base.BaseRepository
import ru.aeon.testapp.data.base.Either
import ru.aeon.testapp.data.remote.dto.auth.LoginRequestDto
import ru.aeon.testapp.data.remote.service.ApiService
import ru.aeon.testapp.domain.error.NetworkError
import ru.aeon.testapp.domain.model.ApiToken
import ru.aeon.testapp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService
) : BaseRepository(), AuthRepository {
    
    override fun requestLoginUsernamePassword(username: String, password: String): Flow<Either<NetworkError, ApiToken>> {
        return doNetworkRequest(
            request = { api.requestAuthPhoneCode(LoginRequestDto(username, password)).onSuccess {  } },
            successful = { body -> Either.Right(body.mapToDomain()) }
        )
    }
}