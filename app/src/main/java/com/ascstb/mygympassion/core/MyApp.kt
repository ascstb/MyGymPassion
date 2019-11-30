package com.ascstb.mygympassion.core

import android.app.Application
import com.ascstb.mygympassion.BuildConfig
import com.ascstb.mygympassion.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.d("MyApp_TAG: onCreate: ")

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            if (BuildConfig.DEBUG) androidLogger()
            androidContext(this@MyApp)
            modules(
                myAppModule +
                        loginModule +
                        userModule +
                        rulesModule +
                        medicQuestionnaireModule
            )
        }
    }
}