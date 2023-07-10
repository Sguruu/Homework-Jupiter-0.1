package com.weather.task7_3notebook.view.extensions

import android.widget.EditText
import com.weather.task7_3notebook.R

fun EditText.checkShowError() {
    val textError = resources.getString(R.string.error_empty_edit_text)
    if (this.text.isEmpty()) {
        this.error = textError
    }
}
