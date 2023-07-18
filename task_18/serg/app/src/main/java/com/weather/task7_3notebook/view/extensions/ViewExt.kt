package com.weather.task7_3notebook.view.extensions

import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import androidx.appcompat.widget.AppCompatSpinner
import com.weather.task7_3notebook.R

fun EditText.checkShowError() {
    val textError = resources.getString(R.string.error_empty_edit_text)
    if (this.text.isEmpty()) {
        this.error = textError
    }
}

fun AppCompatSpinner.setSpinnerFocusable() {
    isFocusableInTouchMode = true

    onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            if (windowToken != null) {
                performClick()

                viewTreeObserver?.addOnWindowFocusChangeListener(object :
                        ViewTreeObserver.OnWindowFocusChangeListener {
                        override fun onWindowFocusChanged(hasFocus: Boolean) {
                            if (hasFocus) {
                                clearFocus()
                                viewTreeObserver?.removeOnWindowFocusChangeListener(this)
                            }
                        }
                    })
            }
        }
    }
}
