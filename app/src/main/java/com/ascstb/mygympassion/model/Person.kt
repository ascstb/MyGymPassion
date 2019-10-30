package com.ascstb.mygympassion.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
    var id: String = "",
    var name: String = "",
    var lastName: String = "",
    var gender: String = "",
    var dateOfBirth: String = "",
    var height: Int = 0,
    var weight: Float = 0f,
    var goalWeight: Float = 0f
) : Parcelable