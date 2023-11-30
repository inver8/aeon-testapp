package ru.aeon.testapp.data.util

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

private val gson = GsonBuilder().create()

internal inline fun <reified T> fromJson(value: String): T? {
    return gson.fromJson(value, object : TypeToken<T>() {}.type)
}