package com.ascstb.mygympassion.di

import com.ascstb.mygympassion.presentation.user.create.UserCreateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    viewModel { UserCreateViewModel() }
}