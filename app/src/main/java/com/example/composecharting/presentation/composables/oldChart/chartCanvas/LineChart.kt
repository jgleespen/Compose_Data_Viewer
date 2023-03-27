package com.example.composecharting.presentation.composables.oldChart.chartCanvas

import android.graphics.Paint
import android.util.Range
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.composecharting.presentation.composables.oldChart.util.GraphData
import com.example.composecharting.presentation.composables.oldChart.util.normalize

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
    //val numberPadding =
    val xPadding = (graphData.graphDataList.totalXMax.value - graphData.graphDataList.totalXMin.value) * 0.1f
    val yPadding = (graphData.graphDataList.totalYMax.value - graphData.graphDataList.totalYMin.value) * 0.1f
    val totalYMax = graphData.graphDataList.totalYMax.value + yPadding
    val totalYMin = graphData.graphDataList.totalYMin.value - yPadding
    val totalXMax = graphData.graphDataList.totalXMax.value + xPadding
    val totalXMin = graphData.graphDataList.totalXMin.value - yPadding
    var offset by remember { mutableStateOf(offset) }
    var scale by remember { mutableStateOf(scale) }
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
                        val newScale = (scale * zoom).coerceIn(1f, 3f)
                        val newOffset =
                            (offset + centroid / oldScale) - (centroid / newScale + pan / oldScale)
                        val xBound: Float
                        val yBound: Float
                        if (newScale - 1f < 0) {
                            xBound = size.width * (newScale - 1f)
                            yBound = size.height * (newScale - 1f)
                        } else {
                            xBound = size.width * (newScale - 1f) / newScale
                            yBound = size.height * (newScale - 1f) / newScale
                        }

                        //println("xBound: $xBound")
                        //println("yBound: $yBound")
                        //println("offset: (${newOffset.x}, ${newOffset.y}")


                        //Mess with this more
                        //I think it is better to allow for a slight zoom out then enforce padding but im not sure, maybe an option. 
                        if (newScale < 1f) {
                            val normalizedScale = newScale.normalize(
                                currentMin = 0.8f,
                                currentMax = 1f,
                                newMin = 0f,
                                newMax = 1f
                            )

                            println("(size.width * xBound) = ${(size.width + xBound) * normalizedScale}")
                            offset = Offset(
                                newOffset.x.coerceIn(xBound, xBound + ((size.width + xBound) * normalizedScale)),
                                newOffset.y.coerceIn(yBound, yBound + ((size.height + yBound) * normalizedScale))
                            )
                        } else {
                            offset = Offset(
                                newOffset.x.coerceIn(0f, xBound),
                                newOffset.y.coerceIn(0f, yBound)
                            )
                        }


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
        //30.dp point padding
        //25.dp grid padding
        val maxX = size.width * (scale - 1f) / scale
        val yBound = size.height * (scale - 1f) / scale
        val width = size.width - 10.dp.toPx()
        drawCircle(
            color = Color.Red,
            radius = 25f,
            center = offset
        )


        val decrementY = (size.height / (totalYMax - totalYMin)) * 5f
        var stepY = size.height
        for (i in 0..(size.height.toInt())) {
            if (stepY < (size.height))
                drawLine(
                    start = Offset(offset.x + (size.width / scale), stepY),
                    end = Offset(offset.x, stepY),
                    color = Color.Black,
                    strokeWidth = 2.dp.toPx() / scale,
                    alpha = 0.3f
                )
            stepY -= decrementY
        }

        drawLine(
            start = Offset(
                x = offset.x - (100f / scale),
                y = offset.y + (size.height - yBound) - (100f / scale)
            ),
            end = Offset(
                x = offset.x + (size.width / scale),
                y = offset.y + (size.height - yBound) - (100f / scale)
            ),
            color = colors.onSurface,
            strokeWidth = 5.dp.toPx() / scale
        )
        drawLine(
            start = Offset(
                offset.x,
                offset.y
            ),
            end = Offset(
                offset.x,
                size.height * scale
            ),
            color = colors.onSurface,
            strokeWidth = 5.dp.toPx() / scale
        )


        graphData.graphDataList.coordinates.forEach { dataSet ->
            val temp =
                graphData.coordinateFormatter.normalizeCoordinates(
                    listX = dataSet.coordinateArray[0],
                    listY = dataSet.coordinateArray[1],
                    yMax = totalYMax,
                    yMin = totalYMin,
                    xMax = totalXMax,
                    xMin = totalXMin,
                    height = size.height,
                    width = width,
                    padding = graphData.padding
                )

            var i = 0
            drawRect(
                color = Color.Transparent,
                topLeft = offset,
                size = Size(width = (width - 15.dp.toPx()) / scale, height = size.height / scale)
            )
            while (i < temp.size - 1) {
                drawLine(
                    color = colors.onSurface,
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
                i += 1
            }
        }
    }
}

