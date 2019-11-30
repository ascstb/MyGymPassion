package com.ascstb.mygympassion.presentation.terms.medic

import com.ascstb.mygympassion.model.MedicQuestion
import com.ascstb.mygympassion.presentation.base.BaseViewModel

class MedicQuestionItemViewModel : BaseViewModel() {
    var medicQuestion: MedicQuestion? = null

    val question: String?
        get() = medicQuestion?.question

    val yes: String?
        get() = medicQuestion?.yes

    val no: String?
        get() = medicQuestion?.no

    val custom: String?
        get() = medicQuestion?.custom
}