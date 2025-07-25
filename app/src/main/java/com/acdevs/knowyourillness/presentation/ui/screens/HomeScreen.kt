package com.acdevs.knowyourillness.presentation.ui.screens

import SymptomChip
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.acdevs.knowyourillness.presentation.ui.components.PredictButton
import com.acdevs.knowyourillness.presentation.ui.components.PredictionRing
import com.acdevs.knowyourillness.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    val filteredList = remember(state.searchText, state.symptomList) {
        state.symptomList.filter {
            it.name.contains(state.searchText, ignoreCase = true)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Selected Symptoms",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 12.dp,bottom = 4.dp)
                )

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.selectedSymptoms.forEach { symptom ->
                        SymptomChip(
                            text = symptom.name,
                            selected = true,
                            onClick = { viewModel.updateSelected(symptom) }
                        )
                    }
                }

                TextField(
                    value = state.searchText,
                    onValueChange = { viewModel.updateSearchText(it) },
                    placeholder = { Text("Search symptoms...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(bottom = 12.dp),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Text(
                    text = "Matching Results",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filteredList
                        .filterNot { selected -> state.selectedSymptoms.any { it.id == selected.id } }
                        .forEach { symptom ->
                            SymptomChip(
                                text = symptom.name,
                                selected = false,
                                onClick = { viewModel.updateSelected(symptom) }
                            )
                        }
                }

            }

            PredictButton(viewModel = viewModel)




            Spacer(modifier = Modifier.height(16.dp)
                .background(MaterialTheme.colorScheme.background))

            @OptIn(ExperimentalFoundationApi::class)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(140.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.predictions) {
                    PredictionRing(
                        disease = it.disease,
                        probability = it.probability,
                        modifier = Modifier.width(140.dp)
                    )
                }
            }

        }
    }
}

