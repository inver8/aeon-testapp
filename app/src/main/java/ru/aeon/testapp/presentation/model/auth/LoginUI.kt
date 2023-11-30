package ru.aeon.testapp.presentation.model.auth

import ru.aeon.testapp.domain.model.ApiToken

data class LoginUI(
    val success: Boolean
)

fun ApiToken.toUI() = LoginUI(this.token.isNotBlank())