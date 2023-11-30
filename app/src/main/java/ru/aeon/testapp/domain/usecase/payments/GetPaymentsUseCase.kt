package ru.aeon.testapp.domain.usecase.payments

import ru.aeon.testapp.domain.repository.PaymentsRepository
import javax.inject.Inject

class GetPaymentsUseCase  @Inject constructor(
    private val repository: PaymentsRepository
) {
    
    operator fun invoke() = repository.getPayments()
}