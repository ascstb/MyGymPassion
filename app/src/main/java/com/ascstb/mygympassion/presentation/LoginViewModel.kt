package com.ascstb.mygympassion.presentation

import androidx.databinding.Bindable
import com.ascstb.mygympassion.model.LoginModel
import com.ascstb.mygympassion.presentation.base.BaseViewModel

class LoginViewModel : BaseViewModel() {
    @get:Bindable
    val user: LoginModel?
        get() {
            if (email.isEmpty() || password.isEmpty()) return null

            return LoginModel(
                email = email,
                password = password
            )
        }

    @Bindable
    var email: String = ""

    @Bindable
    var password: String = ""

}