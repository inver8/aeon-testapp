package ru.aeon.testapp.data.util

interface DataMapper<T> {
    
    fun mapToDomain(): T
}