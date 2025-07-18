package com.acdevs.knowyourillness.domain.usecase

import com.acdevs.knowyourillness.data.repository.SymptomRepository

class LoadSymptomsUseCase(private val repo: SymptomRepository) {
    operator fun invoke() = repo.loadSymptoms()
}