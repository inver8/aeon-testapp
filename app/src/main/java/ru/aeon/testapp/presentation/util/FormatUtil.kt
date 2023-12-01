package ru.aeon.testapp.presentation.util

import android.content.res.Resources
import android.util.TypedValue
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Date

@Suppress("MemberVisibilityCanBePrivate")
object FormatUtil {
    
    private val integerFormat = DecimalFormat("###,###")
    private val floatFormat = DecimalFormat("###,###.00")
    private val dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
    
    var defaultCurrency = "₽"
    
    val currencies = mapOf(
        "RUB" to "₽",
        "EUR" to "€",
        "USD" to "$"
    )
    
    init {
        val decimalSymbols = DecimalFormatSymbols()
        decimalSymbols.decimalSeparator = '.'
        integerFormat.decimalFormatSymbols = decimalSymbols
        floatFormat.decimalFormatSymbols = decimalSymbols
    }
    
    fun decimal(value: Double, force: Boolean = false): String {
        return if (value == value.toInt().toDouble() && !force)
            integerFormat.format(value)
        else
            floatFormat.format(value)
    }
    
    fun Double.moneyFormat(currency: String? = null, useDefault: Boolean = true): String {
        val v = decimal(this)
        val c = if (currency != null)
            currencies[currency] ?: currency
        else if (useDefault)
            defaultCurrency
        else
            return v
        return "$v $c"
    }
    
    fun Date.format(): String = dateTimeFormat.format(this)
}

fun Int.dp(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}

