package ru.aeon.testapp.domain.repository

import ru.aeon.testapp.domain.common.RemoteWrapper
import ru.aeon.testapp.domain.model.Payment

interface PaymentsRepository {
    
    fun fetchPayments(): RemoteWrapper<List<Payment>>
}