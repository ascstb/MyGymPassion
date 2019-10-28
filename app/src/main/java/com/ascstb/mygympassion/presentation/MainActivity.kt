package com.ascstb.mygympassion.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ascstb.mygympassion.R
import com.ascstb.mygympassion.databinding.ActivityMainBinding
import com.ascstb.mygympassion.security.AuthManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.btnLogin.setOnClickListener { onLoginClicked() }
        binding.btnRegister.setOnClickListener { onRegisterClicked() }
    }

    private fun onLoginClicked() {
        Timber.d("MainActivity_TAG: onLoginClicked: ")

        viewModel.user?.let { credentials ->
            viewModel.loading = true
            AuthManager.signInWithEmail(credentials) { successful ->
                Timber.d("MainActivity_TAG: onLoginClicked: $successful")
                viewModel.loading = false
            }
        } ?: Toast.makeText(
            this,
            "Please fill the email account and password correctly",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun onRegisterClicked() {
        Timber.d("MainActivity_TAG: onRegisterClicked: ")

        viewModel.user?.let { credentials ->
            viewModel.loading = true
            AuthManager.createAccountWithEMail(credentials) { successful ->
                Timber.d("MainActivity_TAG: onRegisterClicked: $successful")
                viewModel.loading = false
            }
        } ?: Toast.makeText(
            this,
            "Please fill the email account and password correctly",
            Toast.LENGTH_SHORT
        ).show()
    }
}
