package ru.aeon.testapp.domain.model

data class Payment(
    
    val id: Long,
    
    val title: String,
    
    val amount: String?,
    
    val created: Long?
    
)