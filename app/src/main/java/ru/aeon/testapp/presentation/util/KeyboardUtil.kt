package ru.aeon.testapp.presentation.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardUtil {
    
    fun hide(view: View): Boolean {
        val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}