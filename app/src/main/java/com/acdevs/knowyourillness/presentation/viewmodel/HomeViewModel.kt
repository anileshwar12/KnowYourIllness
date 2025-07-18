package com.acdevs.knowyourillness.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.acdevs.knowyourillness.data.model.Symptom
import com.acdevs.knowyourillness.presentation.state.HomeUiState
import com.acdevs.knowyourillness.domain.usecase.LoadSymptomsUseCase
import com.acdevs.knowyourillness.domain.usecase.PredictDiseaseUseCase

class HomeViewModel(
    private val loadSymptoms: LoadSymptomsUseCase,
    private val predictDisease: PredictDiseaseUseCase
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    fun load() {
        uiState = uiState.copy(symptomList = loadSymptoms())
    }

    fun updateSelected(symptom: Symptom) {
        val updated = uiState.selectedSymptoms.toMutableList()
        if (updated.contains(symptom)) updated.remove(symptom) else updated.add(symptom)
        uiState = uiState.copy(selectedSymptoms = updated)
    }

    fun predict() {
        val symptomIndices = uiState.selectedSymptoms.map { uiState.symptomList.indexOf(it) }
        uiState = uiState.copy(predictions = predictDisease(symptomIndices))
    }
}