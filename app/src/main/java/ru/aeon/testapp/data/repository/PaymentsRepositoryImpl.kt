package ru.aeon.testapp.data.repository

import ru.aeon.testapp.data.base.BaseRepository
import ru.aeon.testapp.data.remote.service.ApiService
import ru.aeon.testapp.domain.common.Either
import ru.aeon.testapp.domain.repository.PaymentsRepository
import java.util.stream.Collectors
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : BaseRepository(), PaymentsRepository {
    
    override fun fetchPayments() = doNetworkRequest(
        request = { api.getPayments() },
        successful = { body ->
            Either.Right(body.stream()
                .map { dto -> dto.mapToDomain() }
                .collect(Collectors.toList()))
        }
    )
}