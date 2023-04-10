package com.weather.t_9_1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormState(
    val valid: Boolean,
    val message: String
) : Parcelable {
    fun setValid(valid: Boolean, message: String = ""): FormState {
        return FormState(valid, message)
    }

    fun setMessage(message: String): FormState {
        return this.copy(message = message)
    }
}
