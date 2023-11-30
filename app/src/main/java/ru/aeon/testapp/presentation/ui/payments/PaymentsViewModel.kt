package ru.aeon.testapp.presentation.ui.payments

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.aeon.testapp.domain.usecase.auth.LogoutUseCase
import ru.aeon.testapp.domain.usecase.payments.FetchPaymentsUseCase
import ru.aeon.testapp.presentation.base.BaseViewModel
import ru.aeon.testapp.presentation.model.payments.PaymentsUI
import ru.aeon.testapp.presentation.model.payments.toUI
import ru.aeon.testapp.presentation.model.state.UIState
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val fetchPaymentsUseCase: FetchPaymentsUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {
    
    private val _paymentsState = MutableStateFlow<UIState<PaymentsUI>>(UIState.Idle())
    val paymentsState = _paymentsState.asStateFlow()
    
    fun fetchPayments() {
        fetchPaymentsUseCase().collectNetworkRequest(_paymentsState) { it.toUI() }
    }
    
    fun logout() {
        logoutUseCase()
    }
}