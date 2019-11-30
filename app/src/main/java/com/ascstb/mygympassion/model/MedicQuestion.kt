package com.ascstb.mygympassion.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicQuestion(
    var question: String,
    var yes: String,
    var no: String,
    var custom: String
):Parcelable