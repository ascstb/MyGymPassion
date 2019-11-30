package com.ascstb.mygympassion.presentation.terms.medic

import androidx.databinding.Bindable
import com.ascstb.mygympassion.model.MedicQuestionnaireTemplate
import com.ascstb.mygympassion.presentation.base.BaseViewModel

class MedicQuestionnaireViewModel : BaseViewModel() {
    @get:Bindable
    var medicQuestionnaire: MedicQuestionnaireTemplate? = null
        set(value) {
            field = value
            notifyChange()
        }

    val terms: String?
        get() = medicQuestionnaire?.terms

    val recyclerItemsViewModel
        get() = medicQuestionnaire?.questions?.map {
            MedicQuestionItemViewModel().apply { medicQuestion = it }
        } ?: emptyList()
}