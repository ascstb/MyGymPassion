package com.ascstb.mygympassion.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RulesAcceptance(
    var personId: String = "",
    var accepted: Boolean = false
) : Parcelable