package com.example.composecharting.presentation.testScreen

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composecharting.data.bundle.GraphData
import com.example.composecharting.data.formatting.CoordinateFormatter
import com.example.composecharting.presentation.chartFormatters.ChartHolderLandscape
import com.example.composecharting.presentation.chartFormatters.ChartWeight

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(navController: NavController, viewModel: TestViewModel = hiltViewModel()) {
    val colors = MaterialTheme.colorScheme
    val graphUtil by remember { viewModel.dataList }
    val configuration = LocalConfiguration.current
    val orientation by remember { mutableStateOf(configuration.orientation) }
    var weights by remember {mutableStateOf(ChartWeight())}
    var textXOffset by remember {mutableStateOf(0f)}
    Scaffold(
        topBar = {
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val graphData by remember {
                mutableStateOf(
                    GraphData(
                        graphUtil,
                        padding = 0f,
                        coordinateFormatter = CoordinateFormatter(),
                    )
                )
            }
            when(orientation){
                Configuration.ORIENTATION_LANDSCAPE -> {
                    weights = ChartWeight(
                        topRowWeight = 0.9f,
                        bottomRowWeight = 0.1f,
                        axisWeight = 0.05f,
                        bodyWeight = 0.9f,
                    )
                    textXOffset = 65f
                }
                else -> {
                    weights = ChartWeight(
                        topRowWeight = 0.9f,
                        bottomRowWeight = 0.05f,
                        axisWeight = 0.08f,
                        bodyWeight = 0.9f,
                    )
                    textXOffset = 55f
                }
            }

            ChartHolderLandscape(
                graphData = graphData,
                colors = colors,
                textXOffset = textXOffset,
                weights = weights
            )
        }

    }
}