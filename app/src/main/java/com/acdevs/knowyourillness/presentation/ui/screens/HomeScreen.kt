package com.acdevs.knowyourillness.presentation.ui.screens

import SymptomChip
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acdevs.knowyourillness.presentation.viewmodel.HomeViewModel
import kotlin.math.roundToInt

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state = viewModel.uiState
    Column(Modifier.padding(16.dp)) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search symptom") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyRow {
            items(state.symptomList) { symptom ->
                SymptomChip(symptom.name, state.selectedSymptoms.contains(symptom)) {
                    viewModel.updateSelected(symptom)
                }
            }
        }

        Button(onClick = { viewModel.predict() }) {
            Text("Predict Disease")
        }

        state.predictions.forEach {
            Text("${it.disease}: ${(it.probability * 100).roundToInt()}%")
        }
    }
}


