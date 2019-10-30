package com.ascstb.mygympassion.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.ascstb.mygympassion.presentation.navigation.Navigation
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {
    protected val navigation by inject<Navigation>()
}