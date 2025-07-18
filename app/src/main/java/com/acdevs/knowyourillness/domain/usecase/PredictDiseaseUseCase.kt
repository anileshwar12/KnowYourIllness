package com.acdevs.knowyourillness.domain.usecase

import com.acdevs.knowyourillness.data.repository.PredictionRepository

class PredictDiseaseUseCase(private val repo: PredictionRepository) {
    operator fun invoke(symptoms: List<Int>) = repo.predict(symptoms)
}