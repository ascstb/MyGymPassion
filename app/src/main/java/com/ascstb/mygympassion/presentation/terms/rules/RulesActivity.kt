package com.ascstb.mygympassion.presentation.terms.rules

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ascstb.mygympassion.R
import com.ascstb.mygympassion.core.Session
import com.ascstb.mygympassion.databinding.RulesActivityLayoutBinding
import com.ascstb.mygympassion.model.RulesAcceptance
import com.ascstb.mygympassion.presentation.navigation.Navigation
import com.ascstb.mygympassion.repository.FirebaseDBManager
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RulesActivity : AppCompatActivity() {
    private lateinit var layout: RulesActivityLayoutBinding
    private val viewModel by viewModel<RulesViewModel>()
    private val navigation by inject<Navigation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBinding()
        getApiInfo()
    }

    private fun setBinding() {
        Timber.d("RulesActivity_TAG: setBinding: ")
        layout = DataBindingUtil.setContentView(this, R.layout.rules_activity_layout)
        layout.lifecycleOwner = this
        layout.viewModel = viewModel

        layout.btnAcceptRules.setOnClickListener { onAcceptRules() }
    }

    private fun getApiInfo() {
        Timber.d("RulesActivity_TAG: getApiInfo: ")
        viewModel.loading = true
        FirebaseDBManager.getRulesContentAsync { html ->
            viewModel.rulesHtml = html
            viewModel.loading = false
        }
    }

    private fun onAcceptRules() {
        Timber.d("RulesActivity_TAG: onAcceptRules: ")
        viewModel.loading = true
        Session.person?.let {
            val rules = RulesAcceptance(
                personId = it.id,
                accepted = true
            )
            FirebaseDBManager.acceptRules(rules) { error, errorMessage ->
                Timber.d("RulesActivity_TAG: onAcceptRules: error: $error, errorMessage: $errorMessage")
                viewModel.loading = false
                if (error) {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    return@acceptRules
                }

                Session.rulesAcceptance = rules

                navigation.navigateNext(this)
            }
        }
    }
}
