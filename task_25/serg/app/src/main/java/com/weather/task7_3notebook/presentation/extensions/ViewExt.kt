package com.weather.task7_3notebook.presentation.extensions

import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.widget.AppCompatSpinner
import com.weather.task7_3notebook.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun EditText.checkShowError() {
    val textError = resources.getString(R.string.error_empty_edit_text)
    if (this.text.isEmpty()) {
        this.error = textError
    }
}

fun EditText.showError(textError: String) {
    this.error = textError
}

fun AppCompatSpinner.setSpinnerFocusable() {
    isFocusableInTouchMode = true

    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
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

fun SearchView.textChangedFlow(): Flow<String> {
    return callbackFlow {
        val queryTextListener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                trySendBlocking(newText.orEmpty())
                return true
            }
        }
        this@textChangedFlow.setOnQueryTextListener(queryTextListener)

        awaitClose {
            this@textChangedFlow.setOnQueryTextListener(null)
        }
    }
}
