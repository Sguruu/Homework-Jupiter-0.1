package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Friend(
    val name: String,
    val surname: String,
    val phoneNumber: String
) : Parcelable