package ru.aeon.testapp.data.remote.repository

import ru.aeon.testapp.data.base.BaseRepository
import ru.aeon.testapp.data.remote.service.ApiService
import ru.aeon.testapp.domain.common.RemoteWrapper
import ru.aeon.testapp.domain.model.Payment
import ru.aeon.testapp.domain.repository.PaymentsRepository
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : BaseRepository(), PaymentsRepository {
    
    override fun getPayments(): RemoteWrapper<List<Payment>> {
        TODO("Not yet implemented")
    }
}