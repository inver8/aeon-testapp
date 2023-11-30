package ru.aeon.testapp.domain.common

import kotlinx.coroutines.flow.Flow
import ru.aeon.testapp.domain.error.NetworkError

internal typealias RemoteWrapper<T> = Flow<Either<NetworkError, T>>