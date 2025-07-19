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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acdevs.knowyourillness.data.model.PredictionResult
import com.acdevs.knowyourillness.data.model.Symptom
import com.acdevs.knowyourillness.presentation.viewmodel.HomeViewModel
import com.acdevs.knowyourillness.presentation.viewmodel.HomeUiState
import kotlin.math.roundToInt

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state = viewModel.uiState

    LaunchedEffect(Unit) { viewModel.load() }

    val filteredList = state.symptomList.filter {
        it.name.contains(state.searchText, ignoreCase = true)
    }

    Column(Modifier.padding(16.dp)) {
        TextField(
            value = state.searchText,
            onValueChange = { viewModel.updateSearchText(it) },
            placeholder = { Text("Search symptom") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyRow(Modifier.padding(vertical = 16.dp)) {
            items(filteredList) { symptom ->
                SymptomChip(
                    text = symptom.name,
                    selected = state.selectedSymptoms.any { it.id == symptom.id },
                    onClick = { viewModel.updateSelected(symptom) }
                )
            }
        }

        Button(onClick = { viewModel.predict() }, Modifier.fillMaxWidth()) {
            Text("Predict Disease")
        }

        state.predictions.forEach {
            Text("${it.disease}: ${(it.probability * 100).roundToInt()}%")
        }
    }
}
