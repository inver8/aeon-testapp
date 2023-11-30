package ru.aeon.testapp.data.remote.dto.payments

import com.google.gson.annotations.SerializedName
import ru.aeon.testapp.data.util.DataMapper
import ru.aeon.testapp.domain.model.Payment

data class PaymentDto(
    
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("amount")
    val amount: String?,
    
    @SerializedName("created")
    val created: Long?
    
) : DataMapper<Payment> {
    
    override fun mapToDomain() = Payment(id, title, amount, created) 
}