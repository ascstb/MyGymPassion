package com.ascstb.mygympassion.di

import com.ascstb.mygympassion.presentation.navigation.Navigation
import com.ascstb.mygympassion.presentation.navigation.NavigationImpl
import org.koin.dsl.module

val myAppModule = module {
    single<Navigation> { NavigationImpl() }
}