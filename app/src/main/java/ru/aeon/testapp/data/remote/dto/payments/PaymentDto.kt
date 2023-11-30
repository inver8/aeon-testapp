package ru.aeon.testapp.data.remote.dto.payments

import com.google.gson.annotations.SerializedName
import ru.aeon.testapp.data.util.DataMapper
import ru.aeon.testapp.domain.model.Payment
import java.util.Date

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
    
    override fun mapToDomain(): Payment {
        val amountDouble = try {
            amount?.toDouble() ?: 0.0
        } catch (ignore: NumberFormatException) {
            0.0
        }
        
        val createdDate: Date? = created?.let {
            Date(it * 1000)
        }
        
        return Payment(id, title, amountDouble, createdDate)
    }    
}