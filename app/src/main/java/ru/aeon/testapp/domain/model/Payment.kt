package ru.aeon.testapp.domain.model

import java.util.Date

data class Payment(
    
    val id: Long,
    
    val title: String,
    
    val amount: Double,
    
    val created: Date?
    
)