package com.weather.task7_3notebook.utils

import android.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

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
