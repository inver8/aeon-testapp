package ru.aeon.testapp.domain.usecase.auth

import ru.aeon.testapp.domain.repository.AuthRepository
import javax.inject.Inject

class IsAuthorizedUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    
    operator fun invoke() = repository.isAuthorized()
}