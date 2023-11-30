package ru.aeon.testapp.domain.usecase.auth

import ru.aeon.testapp.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUsernamePasswordUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    
    operator fun invoke(username: String, password: String) = repository.requestLoginUsernamePassword(username, password)
}