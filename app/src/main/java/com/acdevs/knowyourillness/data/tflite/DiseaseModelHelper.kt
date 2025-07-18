package com.acdevs.knowyourillness.data.tflite

import android.content.Context
import org.tensorflow.lite.Interpreter
import com.acdevs.knowyourillness.data.model.PredictionResult
import org.json.JSONArray
import org.tensorflow.lite.support.common.FileUtil

class DiseaseModelHelper(context: Context) {
    private val interpreter: Interpreter
    private val labelList: List<String>

    init {
        val model = FileUtil.loadMappedFile(context, "disease_model.tflite")
        interpreter = Interpreter(model)
        val labelsJson = context.assets.open("label_classes.json").bufferedReader().use { it.readText() }
        labelList = JSONArray(labelsJson).let { arr -> List(arr.length()) { arr.getString(it) } }
    }

    fun predict(indices: List<Int>): List<PredictionResult> {
        val input = FloatArray(132) // Change 132 based on your model input size
        indices.forEach { input[it] = 1f }

        val output = Array(1) { FloatArray(labelList.size) }
        interpreter.run(arrayOf(input), output)

        return output[0].mapIndexed { i, prob ->
            PredictionResult(labelList[i], prob)
        }.sortedByDescending { it.probability }.take(4)
    }
}
