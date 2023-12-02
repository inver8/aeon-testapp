package ru.aeon.testapp.data.base

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import ru.aeon.testapp.BuildConfig
import ru.aeon.testapp.data.remote.dto.base.ResponseDto
import ru.aeon.testapp.data.util.DataMapper
import ru.aeon.testapp.domain.common.Either
import ru.aeon.testapp.domain.error.NetworkError
import java.io.InterruptedIOException
import java.net.ConnectException

abstract class BaseRepository {
    
    protected fun <T : DataMapper<S>, S> doNetworkRequestWithMapping(
        request: suspend () -> Response<ResponseDto<T>>
    ): Flow<Either<NetworkError, S>> = doNetworkRequest(request) { body ->
        Either.Right(body.mapToDomain())
    }
    
    protected fun <T : DataMapper<S>, S> doNetworkRequestListWithMapping(
        request: suspend () -> Response<ResponseDto<List<T>>>
    ): Flow<Either<NetworkError, List<S>>> = doNetworkRequest(request) { body ->
        Either.Right(
            body.map { dto -> dto.mapToDomain() }
        )
    }
    
    private fun <T, S> doNetworkRequest(
        request: suspend () -> Response<ResponseDto<T>>,
        successful: (T) -> Either.Right<S>
    ): Flow<Either<NetworkError, S>> = flow {
        request().let {
            if (it.isSuccessful && it.body() != null) {
                val body = it.body()!!
                if (body.success) {
                    emit(successful.invoke(body.response!!))
                } else {
                    emit(Either.Left(NetworkError.Api(body.error!!)))
                }
            } else {
                emit(Either.Left(NetworkError.Unexpected("Error Occurred!")))
            }
        }
    }.flowOn(Dispatchers.IO).catch { exception ->
        when (exception) {
            is InterruptedIOException -> {
                val message = exception.localizedMessage ?: "Timeout Occurred!"
                if (BuildConfig.DEBUG) Log.e(TAG, exception.toString())
                emit(Either.Left(NetworkError.Timeout(message)))
            }

            is ConnectException -> {
                val message = exception.localizedMessage ?: "Connection Error Occurred!"
                if (BuildConfig.DEBUG) Log.e(TAG, exception.toString())
                emit(Either.Left(NetworkError.Connection(message)))
            }

            else -> {
                val message = exception.localizedMessage ?: "Error Occurred!"
                if (BuildConfig.DEBUG) Log.e(TAG, exception.toString())
                emit(Either.Left(NetworkError.Unexpected(message)))
            }
        }
    }
    
    protected inline fun <T : Response<S>, S> T.onSuccess(block: (S) -> Unit): T {
        this.body()?.let(block)
        return this
    }
    
    companion object {
        private val TAG = BaseRepository::class.java.simpleName
    }
}