package com.ascstb.mygympassion.di

import com.ascstb.mygympassion.presentation.terms.rules.RulesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val rulesModule = module {
    viewModel { RulesViewModel() }
}