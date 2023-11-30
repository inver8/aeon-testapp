package ru.aeon.testapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.aeon.testapp.domain.common.Either
import ru.aeon.testapp.domain.error.NetworkError
import ru.aeon.testapp.presentation.model.state.UIState

abstract class BaseViewModel : ViewModel() {
    
    protected fun <T, S> Flow<Either<NetworkError, T>>.collectNetworkRequest(
        state: MutableStateFlow<UIState<S>>,
        mapToUI: (T) -> S
    ) = collectEither(state) {
        UIState.Success(mapToUI(it))
    }
    
    private fun <T, S> Flow<Either<NetworkError, T>>.collectEither(
        state: MutableStateFlow<UIState<S>>,
        successful: (T) -> UIState.Success<S>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UIState.Loading()
            this@collectEither.collect {
                when (it) {
                    is Either.Left -> state.value = UIState.Error(it.value)
                    is Either.Right -> state.value = successful(it.value)
                }
            }
        }
    }
}