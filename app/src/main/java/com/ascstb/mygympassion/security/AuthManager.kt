package com.ascstb.mygympassion.security

import com.ascstb.mygympassion.core.Session
import com.ascstb.mygympassion.model.LoginModel
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

object AuthManager {
    private val auth = FirebaseAuth.getInstance()

    fun createAccountWithEMail(loginModel: LoginModel, onDone: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(loginModel.email, loginModel.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("AuthManager_TAG: createAccountWithEMail: successful")
                    Session.user = auth.currentUser
                    onDone(true)
                } else {
                    Timber.d("AuthManager_TAG: createAccountWithEMail: unsuccessful: exception: ${task.exception}")
                    Session.user = null
                    onDone(false)
                }
            }
    }

    fun signInWithEmail(loginModel: LoginModel, onDone: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(loginModel.email, loginModel.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("AuthManager_TAG: singInWithEmail: successful")
                    Session.user = auth.currentUser
                    onDone(true)
                } else {
                    Timber.d("AuthManager_TAG: singInWithEmail: unsuccessful: exception: ${task.exception}")
                    Session.user = null
                    onDone(false)
                }
            }
    }

    fun isSessionValid() = (auth.currentUser != null)

    fun signOut() {
        Timber.d("AuthManager_TAG: signOut: ")
        auth.signOut()
    }
}