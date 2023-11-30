package ru.aeon.testapp.data.remote.dto.payments

import com.google.gson.annotations.SerializedName

data class PaymentDto(
    
    @SerializedName("id")
    val id: Long,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("amount")
    val amount: Double?,
    
    @SerializedName("created")
    val created: Long?
    
)