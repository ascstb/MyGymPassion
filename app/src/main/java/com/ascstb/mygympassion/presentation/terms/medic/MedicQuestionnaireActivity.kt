package com.ascstb.mygympassion.presentation.terms.medic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ascstb.mygympassion.R
import com.ascstb.mygympassion.databinding.MedicQuestionnaireActivityBinding
import com.ascstb.mygympassion.repository.FirebaseDBManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MedicQuestionnaireActivity : AppCompatActivity() {

    private lateinit var layout: MedicQuestionnaireActivityBinding
    private val viewModel by viewModel<MedicQuestionnaireViewModel>()
    private lateinit var questionsAdapter: RVMedicQuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()

        initRecyclerView()
        getApiInfo()
    }

    private fun setBinding() {
        Timber.d("MedicQuestionnaireActivity_TAG: setBinding: ")
        layout = DataBindingUtil.setContentView(this, R.layout.medic_questionnaire_activity)
        layout.lifecycleOwner = this
        layout.viewModel = viewModel
    }

    private fun initRecyclerView() {
        questionsAdapter = RVMedicQuestionAdapter{ _, question ->
            Timber.d("MedicQuestionnaireActivity_TAG: initRecyclerView: $question")
        }

        layout.rvQuestions.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = questionsAdapter
        }

        questionsAdapter.itemList = emptyList()
    }

    private fun getApiInfo() {
        Timber.d("MedicQuestionnaireActivity_TAG: getApiInfo: ")
        viewModel.loading = true
        FirebaseDBManager.getMedicQuestionnaireTemplateAsync { mq ->
            viewModel.medicQuestionnaire = mq
            questionsAdapter.itemList = viewModel.recyclerItemsViewModel
            viewModel.loading = false
        }
    }
}
