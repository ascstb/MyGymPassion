package com.ascstb.mygympassion.di

import com.ascstb.mygympassion.presentation.terms.medic.MedicQuestionnaireViewModel
import com.ascstb.mygympassion.presentation.terms.rules.RulesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val rulesModule = module {
    viewModel { RulesViewModel() }
}

val medicQuestionnaireModule = module {
    viewModel { MedicQuestionnaireViewModel() }
}