package com.acdevs.knowyourillness.data.repository

import android.content.Context
import com.acdevs.knowyourillness.data.model.PredictionResult
import com.acdevs.knowyourillness.data.tflite.DiseaseModelHelper

class PredictionRepository(context: Context) {
    private val model = DiseaseModelHelper(context)

    fun predict(symptomIndices: List<Int>): List<PredictionResult> {
        return model.predict(symptomIndices)
    }
}