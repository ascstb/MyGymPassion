package com.ascstb.mygympassion.presentation.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ascstb.mygympassion.presentation.MainActivity
import com.ascstb.mygympassion.presentation.dashboard.DashboardActivity
import com.ascstb.mygympassion.presentation.user.create.UserCreateActivity
import timber.log.Timber

class NavigationImpl : Navigation {
    override fun navigateNext(fromActivity: AppCompatActivity, extras: Bundle?) {
        Timber.d("NavigationImpl_TAG: navigateNext: from: ${fromActivity::class.java.simpleName}, extras: $extras")
        val toActivity = getNextScreen(fromActivity) ?: return

        navigate(fromActivity, toActivity, extras)
    }

    override fun navigateToApp(context: Context, app: Navigation.DeepLink, extras: Bundle?) {
        Timber.d("NavigationImpl_TAG: navigateToApp: app: $app")
        try {
            val innerApp =
                Navigation.DeepLink.getDeepLink(app.innerLink)

            //region Check / Open third party app if applies
            if (innerApp == null) {
                val intent =
                    context.packageManager.getLaunchIntentForPackage(app.innerLink) ?: return
                context.startActivity(intent)
                return
            }
            //endregion

            val appView = when (innerApp) {
                Navigation.DeepLink.DASHBOARD -> DashboardActivity::class.java
                else -> null
            } ?: return

            navigate(context, appView)

        } catch (e: Exception) {
            Timber.d("NavigationImpl_TAG: navigateToApp: error: ${e.message}")
        }
    }

    private fun getNextScreen(fromActivity: AppCompatActivity): Class<*>? =
        when (fromActivity::class) {
            MainActivity::class -> UserCreateActivity::class.java
            else -> null
        }

    private fun <T> navigate(
        context: Context,
        view: Class<T>,
        extras: Bundle? = null
    ) {
        Timber.d("NavigationImpl_TAG: navigate: ")
        val intent = Intent(context, view)
        if (extras != null) {
            intent.putExtras(extras)
        }

        context.startActivity(intent)
    }
}