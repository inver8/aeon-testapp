package ru.aeon.testapp.presentation.util

import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible

fun EditText.getString(): String {
    return text?.toString() ?: ""
}

fun TextView.setTextAndVisibility(text: String?) {
    if (text.isNullOrBlank())
        isVisible = false
    else {
        this.text = text
        isVisible = true
    }
}

fun TextView.setTextAndVisibility(@StringRes stringRes: Int) {
    setTextAndVisibility(this.resources.getString(stringRes))
}