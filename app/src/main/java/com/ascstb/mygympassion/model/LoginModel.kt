package com.ascstb.mygympassion.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginModel(
    var email: String,
    var password: String
) : Parcelable