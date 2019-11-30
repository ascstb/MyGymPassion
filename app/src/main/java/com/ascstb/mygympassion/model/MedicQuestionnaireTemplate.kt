package com.ascstb.mygympassion.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicQuestionnaireTemplate(
    var terms: String,
    var questions: List<MedicQuestion>
) : Parcelable