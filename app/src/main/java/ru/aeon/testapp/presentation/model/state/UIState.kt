package ru.aeon.testapp.presentation.model.state

import ru.aeon.testapp.domain.error.NetworkError


sealed class UIState<T> {
    class Idle<T> : UIState<T>()
    class Loading<T>(val cache: T? = null) : UIState<T>()
    class Error<T>(val error: NetworkError) : UIState<T>()
    class Success<T>(val data: T) : UIState<T>()
}