package com.acdevs.knowyourillness.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.acdevs.knowyourillness.data.model.PredictionResult
import com.acdevs.knowyourillness.data.model.Symptom
import com.acdevs.knowyourillness.domain.usecase.LoadSymptomsUseCase
import com.acdevs.knowyourillness.domain.usecase.PredictDiseaseUseCase

class HomeViewModel(
    private val loadSymptoms: LoadSymptomsUseCase,
    private val predictDisease: PredictDiseaseUseCase
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set



    fun load() {
        val symptoms = loadSymptoms()
        println("Loaded symptoms: ${symptoms.map { it.name }}")
        uiState = uiState.copy(symptomList = symptoms)
    }

//    fun load() {
//        val dummy = listOf(
//            Symptom("Fever", 0),
//            Symptom("Cough", 1),
//            Symptom("Headache", 2),
//            Symptom("Fatigue", 3)
//        )
//        uiState = uiState.copy(symptomList = dummy)
//    }



    fun updateSearchText(value: String) {
        uiState = uiState.copy(searchText = value)
    }

    fun updateSelected(symptom: Symptom) {
        val updated = uiState.selectedSymptoms.toMutableList()
        if (updated.any { it.id == symptom.id }) {
            updated.removeIf { it.id == symptom.id }
        } else {
            updated.add(symptom)
        }
        uiState = uiState.copy(selectedSymptoms = updated)
    }



    fun predict() {
        val symptomIndices = uiState.selectedSymptoms
            .map { uiState.symptomList.indexOf(it) }
            .filter { it >= 0 }
        if (symptomIndices.isEmpty()) return
        uiState = uiState.copy(predictions = predictDisease(symptomIndices))
    }
}
