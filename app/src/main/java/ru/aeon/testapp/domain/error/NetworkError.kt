package ru.aeon.testapp.domain.error

import androidx.annotation.StringRes
import ru.aeon.testapp.R
import ru.aeon.testapp.data.remote.dto.base.ApiErrorDto

sealed class NetworkError(@StringRes val defaultUserMessageRes: Int = 0) {
    
    class Unexpected(
        val throwableMessage: String?
    ) : NetworkError(R.string.server_error)
    
    class Api(val apiError: ApiErrorDto?) : NetworkError()
    
    class Timeout(
        val throwableMessage: String?
    ) : NetworkError(R.string.server_unavailable)
    
    class Connection(
        val throwableMessage: String?
    ) : NetworkError(R.string.connection_error)
}