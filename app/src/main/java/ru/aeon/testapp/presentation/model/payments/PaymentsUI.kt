package ru.aeon.testapp.presentation.model.payments

import ru.aeon.testapp.domain.model.Payment

data class PaymentsUI(
    val payments: List<Payment>
) 

fun List<Payment>.toUI() = PaymentsUI(this)