package com.acdevs.knowyourillness.presentation.viewmodel

import com.acdevs.knowyourillness.data.model.PredictionResult
import com.acdevs.knowyourillness.data.model.Symptom

data class HomeUiState(
    val symptomList: List<Symptom> = emptyList(),
    val selectedSymptoms: List<Symptom> = emptyList(),
    val predictions: List<PredictionResult> = emptyList(),
    val searchText: String = ""
)
