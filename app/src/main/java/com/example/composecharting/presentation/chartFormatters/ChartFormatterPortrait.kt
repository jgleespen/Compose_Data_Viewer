package com.example.composecharting.presentation.chartFormatters

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.sp
import com.company.strengthtracker.presentation.test_screen.graph_utils.XAxisLandscape
import com.company.strengthtracker.presentation.test_screen.graph_utils.YAxisLandscape
import com.example.composecharting.data.bundle.GraphData
import com.example.composecharting.presentation.chartCanvas.LineChart

@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartHolderPortrait(
    graphData: GraphData,
    colors: ColorScheme,
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    // textPaint to construct text objects within the graph
    val density = LocalDensity.current
    val textPaint =
        remember(density) {
            Paint().apply {
                color = Color.WHITE
                textAlign = Paint.Align.LEFT
                textSize = density.run { 12.sp.toPx() }
            }
        }
    textPaint.isAntiAlias = true
    var pan by remember { mutableStateOf(Offset.Zero) }

    Column(
        modifier = Modifier
            .fillMaxSize(1f).clipToBounds()
            .onGloballyPositioned { layoutCoordinates ->
                val rect: Rect = layoutCoordinates.boundsInRoot()
            },
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
                .background(colors.surface),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {

            //YAxis
            Column(
                modifier = Modifier
                    .background(colors.surface)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(0.07f)
            ) {
                YAxisLandscape(
                    colors = colors,
                    scale = scale,
                    offset = offset,
                    graphData = graphData,
                    textXOffset = 65f
                )


            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f)
                    .background(colors.surface)
                    .clip(RectangleShape)

            ) {
                LineChart(
                    graphData = GraphData(
                        graphDataList = graphData.graphDataList,
                        padding = graphData.padding,
                        coordinateFormatter = graphData.coordinateFormatter
                    ), colors = colors, scale = scale, offset = offset,
                    onScaleChanged = {
                        scale = it
                    },
                    onOffsetChanged = {
                        offset = it
                    },
                    gestureListener = { centroid, panAmount, zoom ->
                        pan = panAmount
                    },
                    textPaint = textPaint,
                )

            }
        }
        Row(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .background(colors.surface)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(0.06f)
            ) {

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f)
                    .background(colors.surface)

            ) {
                XAxisLandscape(
                    colors = colors,
                    scale = scale,
                    offset = offset,
                    graphData = graphData
                )
            }
        }
    }
}
