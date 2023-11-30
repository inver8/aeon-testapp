package ru.aeon.testapp.presentation.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

class OnTextChanged(
    private val editText: EditText,
    private val listener: OnTextChangedListener
) : TextWatcher {
    
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
       
    }
    
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (editText.hasFocus()) 
            listener.onTextChanged(editText, s)
    }
    
    override fun afterTextChanged(s: Editable?) {
    }
    
    fun interface OnTextChangedListener {
        fun onTextChanged(textView: TextView, s: CharSequence?)
    }
}