package ru.aeon.testapp.data.repository

import ru.aeon.testapp.data.base.BaseRepository
import ru.aeon.testapp.data.remote.service.ApiService
import ru.aeon.testapp.domain.repository.PaymentsRepository
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
    private val api: ApiService
) : BaseRepository(), PaymentsRepository {
    
    override fun fetchPayments() = doNetworkRequestListWithMapping { 
        api.getPayments() 
    }
    
}