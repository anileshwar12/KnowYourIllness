package com.acdevs.knowyourillness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.acdevs.knowyourillness.data.repository.PredictionRepository
import com.acdevs.knowyourillness.data.repository.SymptomRepository
import com.acdevs.knowyourillness.domain.usecase.LoadSymptomsUseCase
import com.acdevs.knowyourillness.domain.usecase.PredictDiseaseUseCase
import com.acdevs.knowyourillness.presentation.ui.screens.HomeScreen
import com.acdevs.knowyourillness.presentation.viewmodel.HomeViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val symptomRepo = SymptomRepository(this)
                val predictionRepo = PredictionRepository(this)
                val viewModel = HomeViewModel(
                    LoadSymptomsUseCase(symptomRepo),
                    PredictDiseaseUseCase(predictionRepo)
                )
                viewModel.load()
                HomeScreen(viewModel)
            }
        }
    }
}