package com.ascstb.mygympassion.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ascstb.mygympassion.R
import com.ascstb.mygympassion.core.Session
import com.ascstb.mygympassion.databinding.ActivityMainBinding
import com.ascstb.mygympassion.presentation.navigation.Navigation
import com.ascstb.mygympassion.repository.FirebaseDBManager
import com.ascstb.mygympassion.security.AuthManager
import com.google.zxing.integration.android.IntentIntegrator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<LoginViewModel>()
    private val navigation by inject<Navigation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding()

        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {

        }
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
            AuthManager.signInWithEmail(credentials) { successful, errorMessage ->
                Timber.d("MainActivity_TAG: onLoginClicked: successful: $successful")
                if (successful) {
                    Session.user?.let { user ->
                        FirebaseDBManager.getPersonAsync(user.uid) { person ->
                            Timber.d("MainActivity_TAG: onLoginClicked: getPersonAsync: $person")
                            Session.person = person
                            navigation.navigateNext(this)
                        }
                    }
                } else {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
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
            AuthManager.createAccountWithEMail(credentials) { successful, errorMessage ->
                Timber.d("MainActivity_TAG: onRegisterClicked: $successful")
                viewModel.loading = false
                if (successful) {
                    navigation.navigateNext(this)
                } else {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        } ?: Toast.makeText(
            this,
            "Please fill the email account and password correctly",
            Toast.LENGTH_SHORT
        ).show()
    }
}
