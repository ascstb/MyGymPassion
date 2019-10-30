package com.ascstb.mygympassion.presentation.navigation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

interface Navigation {
    fun navigateNext(fromActivity: AppCompatActivity, extras: Bundle? = null)
    fun navigateToApp(context: Context, app: DeepLink, extras: Bundle? = null)

    enum class DeepLink(val innerLink: String) {
        DASHBOARD(innerLink = "dashboard"),
        SESSIONS(innerLink = "sessions"),
        SPARRING(innerLink = "sparring"),
        MUSIC(innerLink = "music"),
        CALENDAR(innerLink = "calendar"),
        MOVES(innerLink = "moves")
        ;

        companion object {
            fun getDeepLink(name: String) = values().find { it.innerLink == name }
        }
    }
}