package com.ascstb.mygympassion.presentation.terms.rules

import androidx.databinding.Bindable
import com.ascstb.mygympassion.presentation.base.BaseViewModel

class RulesViewModel : BaseViewModel() {
    @get:Bindable
    var rulesHtml: String? = ""
        set(value) {
            field = value ?: ""
            notifyChange()
        }
}