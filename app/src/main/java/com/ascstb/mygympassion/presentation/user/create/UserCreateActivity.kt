package com.ascstb.mygympassion.presentation.user.create

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ascstb.mygympassion.R
import com.ascstb.mygympassion.core.Session
import com.ascstb.mygympassion.databinding.UserCreateActivityLayoutBinding
import com.ascstb.mygympassion.presentation.navigation.Navigation
import com.ascstb.mygympassion.repository.FirebaseDBManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class UserCreateActivity : AppCompatActivity() {
    private val navigation by inject<Navigation>()
    private lateinit var binding: UserCreateActivityLayoutBinding
    private val viewModel by viewModel<UserCreateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("UserCreateActivity_TAG: onCreate: ")

        setBinding()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.user_create_activity_layout)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.btnContinue.setOnClickListener { onContinueClicked() }
    }

    private fun onContinueClicked() {
        Timber.d("UserCreateActivity_TAG: onContinueClicked: ")
        FirebaseDBManager.createPerson(viewModel.person) { successful, errorMessage ->
            if (!successful) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                return@createPerson
            }

            Session.person = viewModel.person
            navigation.navigateToApp(this, Navigation.DeepLink.DASHBOARD)
        }
    }
}
