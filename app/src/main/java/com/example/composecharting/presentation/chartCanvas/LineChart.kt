package com.example.composecharting.presentation.chartCanvas

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.composecharting.data.bundle.GraphData
import kotlin.random.Random

// TODO
// -> curved line option
// -> add coloring options for lines/grid
@Composable
fun LineChart(
    graphData: GraphData,
    colors: ColorScheme,
    scale: Float,
    offset: Offset,
    textPaint: Paint,
    onScaleChanged: (Float) -> Unit,
    onOffsetChanged: (Offset) -> Unit,
    gestureListener: (centroid: Offset, pan: Offset, zoom: Float) -> Unit
) {
    val totalYMax = graphData.graphDataList.totalYMax.value + 20f
    val totalYMin = graphData.graphDataList.totalYMin.value - 20f
    val totalXMax = graphData.graphDataList.totalXMax.value
    val totalXMin = graphData.graphDataList.totalYMin.value
    var offset by remember { mutableStateOf(offset) }
    var scale by remember { mutableStateOf(scale) }
    var maxX by remember { mutableStateOf(0f) }
    var maxY by remember { mutableStateOf(0f) }
    Canvas(
        modifier =
        Modifier
            .fillMaxSize()
            .clip(shape = RectangleShape)
            .background(color = colors.surface)
            .pointerInput(Unit) {
                detectTransformGestures(
                    panZoomLock = false,
                    onGesture = { centroid, pan, zoom, _ ->
                        val oldScale = scale
                        val newScale = (scale * zoom).coerceIn(1f, 2f)
                        val newOffset =
                            (offset + centroid / oldScale) - (centroid / newScale + pan / oldScale)
                        maxX = (size.width) * (newScale - 1f) / newScale
                        maxY = (size.height) * (newScale - 1f) / newScale
                        offset =
                            Offset(newOffset.x.coerceIn(0f, maxX), newOffset.y.coerceIn(0f, maxY))
                        scale = newScale
                        onScaleChanged(scale)
                        onOffsetChanged(offset)
                        gestureListener(centroid, pan, zoom)
                    }
                )
            }
            .graphicsLayer {
                translationX = (-offset.x * scale)
                translationY = (-offset.y * scale)
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin(0f, 0f)
            }
    ) {
        val width = size.width


        val decrementY = (size.height / (totalYMax - totalYMin)) * 5f
        var stepY = size.height
        for (i in 0..(size.height.toInt())) {
            if (stepY < (size.height))
                drawLine(
                    start = Offset(offset.x + (size.width / scale), stepY),
                    end = Offset(offset.x + 50f, stepY),
                    color = Color.Black,
                    strokeWidth = 2.dp.toPx() / scale,
                    alpha = 0.3f
                )
            stepY -= decrementY
        }
/*
        drawLine(
            start = Offset(
                offset.x,
                offset.y
            ),
            end = Offset(
                offset.x,
                size.height
            ),
            color = colors.onSurface,
            strokeWidth = 5.dp.toPx() / scale
        )
*/

        Log.d("XMAX: ", "${graphData.graphDataList.totalXMax}")

        graphData.graphDataList.coordinates.forEach { dataSet ->
            val temp =
                graphData.coordinateFormatter.normalizeCoordinates(
                    listX = dataSet.coordinateArray[0],
                    listY = dataSet.coordinateArray[1],
                    yMax = totalYMax,
                    yMin = totalYMin,
                    xMax = dataSet.xMax,
                    xMin = dataSet.xMin,
                    height = size.height,
                    width = width,
                    padding = graphData.padding
                )

            val c = Color(android.graphics.Color.rgb(Random.nextInt(), Random.nextInt(), Random.nextInt()))
            for (i in 0..temp.size - 2) {
                drawLine(
                    color = c,
                    start = temp[i],
                    end = temp[i + 1],
                    strokeWidth = 3.5f.dp.toPx()
                )
                drawLine(
                    color = colors.onSurface,
                    strokeWidth = 5.dp.toPx() / scale,
                    start = Offset(
                        temp[i].x,
                        temp[i].y + 6.dp.toPx()
                    ),
                    end = Offset(
                        temp[i].x,
                        temp[i].y - 6.dp.toPx()
                    )
                )
            }
        }

        drawLine(
            start = Offset(
                x = offset.x,
                y = offset.y + (size.height - maxY)
            ),
            end = Offset(
                x = offset.x + (size.width / scale),
                y = offset.y + (size.height - maxY)
            ),
            color = colors.onSurface,
            strokeWidth = 5.dp.toPx() / scale
        )

        // Y Axis
        drawLine(
            start = Offset(
                offset.x,
                offset.y
            ),
            end = Offset(
                offset.x,
                size.height
            ),
            color = colors.onSurface,
            strokeWidth = 5.dp.toPx() / scale
        )
    }
}

