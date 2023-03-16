package com.example.composecharting.presentation.composables.newchart

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun rememberLineChartState(
    data: List<List<Offset>>,
    scope: CoroutineScope
): LineChartState = LineChartState(data, scope)

class LineChartState(
    private val data: List<List<Offset>>,
    private val scope: CoroutineScope
) {
    var maximumY: Float by mutableStateOf(Float.NEGATIVE_INFINITY)
        private set
    var maximumX: Float by mutableStateOf(Float.POSITIVE_INFINITY)
        private set

    init {
        getMaximumValues()
    }

    private fun normalizeLists() {

    }

    private fun getMaximumValues() {

    }
}
