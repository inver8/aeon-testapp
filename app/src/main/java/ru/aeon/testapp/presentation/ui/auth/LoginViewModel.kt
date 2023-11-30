package ru.aeon.testapp.presentation.ui.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.aeon.testapp.domain.usecase.auth.LoginUsernamePasswordUseCase
import ru.aeon.testapp.presentation.base.BaseViewModel
import ru.aeon.testapp.presentation.model.auth.LoginUI
import ru.aeon.testapp.presentation.model.auth.toUI
import ru.aeon.testapp.presentation.model.state.UIState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUsernamePassword: LoginUsernamePasswordUseCase
): BaseViewModel() {
    
    private val _loginState = MutableStateFlow<UIState<LoginUI>>(UIState.Idle())
    val loginState = _loginState.asStateFlow()
    
    fun loginWithUsernamePassword(username: String, password: String) {
        loginUsernamePassword(username, password).collectNetworkRequest(_loginState) { it.toUI() }
    }
    
    fun setLoginStateIdle() {
        _loginState.value = UIState.Idle()
    }
}