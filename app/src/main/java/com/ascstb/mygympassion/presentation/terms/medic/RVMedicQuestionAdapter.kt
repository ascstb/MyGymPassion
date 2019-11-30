package com.ascstb.mygympassion.presentation.terms.medic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ascstb.mygympassion.databinding.MedicQuestionnaireItemLayoutBinding
import com.ascstb.mygympassion.model.MedicQuestion
import com.ascstb.mygympassion.presentation.base.BaseRVAdapter

class RVMedicQuestionAdapter(
    listener: (View, MedicQuestion) -> Unit
) : BaseRVAdapter<MedicQuestion, MedicQuestionItemViewModel, MedicQuestionnaireItemLayoutBinding>(
    listener
) {
    override fun inflateDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MedicQuestionnaireItemLayoutBinding =
        MedicQuestionnaireItemLayoutBinding.inflate(inflater, container, false)

    override fun getBindItem(itemViewModel: MedicQuestionItemViewModel): MedicQuestion? =
        itemViewModel.medicQuestion
}