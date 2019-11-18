package com.ascstb.mygympassion.presentation.user.create

import android.app.DatePickerDialog
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
import java.util.*

class UserCreateActivity : AppCompatActivity() {
    private val navigation by inject<Navigation>()
    private lateinit var layout: UserCreateActivityLayoutBinding
    private val viewModel by viewModel<UserCreateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("UserCreateActivity_TAG: onCreate: ")

        setBinding()
    }

    private fun setBinding() {
        layout = DataBindingUtil.setContentView(this, R.layout.user_create_activity_layout)
        layout.lifecycleOwner = this
        layout.viewModel = viewModel

        layout.etDOB.setOnFocusChangeListener { _, hasFocus ->
            Timber.d("UserCreateActivity_TAG: setBinding: etDOB.setOnFocusChangeListener: $hasFocus")
            if (!hasFocus) return@setOnFocusChangeListener

            DatePickerDialog(
                this,
                R.style.AlertDialogTheme,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    viewModel.dobCalendar.apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    viewModel.notifyChange()
                },
                viewModel.dobCalendar.get(Calendar.YEAR),
                viewModel.dobCalendar.get(Calendar.MONTH),
                viewModel.dobCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        layout.btnContinue.setOnClickListener { onContinueClicked() }
    }

    private fun onContinueClicked() {
        Timber.d("UserCreateActivity_TAG: onContinueClicked: ")
        viewModel.loading = true
        FirebaseDBManager.createPerson(viewModel.person) { successful, errorMessage ->
            viewModel.loading = false
            if (!successful) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                return@createPerson
            }

            Session.person = viewModel.person
            navigation.navigateToApp(this, Navigation.DeepLink.DASHBOARD)
        }
    }
}
