package com.company.strengthtracker.presentation.test_screen.graph_utils

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.composecharting.presentation.composables.oldChart.util.GraphData


@Composable
fun YAxisLandscape(
    graphData: GraphData,
    colors: ColorScheme,
    scale: Float,
    offset: Offset,
    textXOffset: Float
) {
    val density = LocalDensity.current
    val axisPadding = 20f
    val totalYMax = graphData.graphDataList.totalYMax.value + axisPadding
    val totalYMin = graphData.graphDataList.totalYMin.value - axisPadding
    val textPaint = remember { mutableStateOf(Paint()) }
    textPaint.value =
        Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.RIGHT
            textSize = density.run { 12.dp.toPx() }
        }
    textPaint.value.isSubpixelText = true

    textPaint.value.isAntiAlias = true
    textPaint.value.isLinearText = true
    Canvas(modifier = Modifier
        .fillMaxSize()
        .clipToBounds()
        .background(color = colors.surface)
        .graphicsLayer {
            scaleY = scale
            scaleX = scale
            translationY = -offset.y * scale
            translationX = -offset.x * scale
            transformOrigin = TransformOrigin(0f, 0f)
        }
    ) {
        val maxY = size.height * (scale - 1f) / scale
        val maxX = size.width * (scale - 1f) / scale

        textPaint.value.textSize /= scale


/*
        var text = ""
        val stepSize = size.height / (totalYMax - totalYMin)
*/
        var text = totalYMin
        val decrementY = (size.height / (totalYMax - totalYMin)) * 5f
        var step = size.height
        val increment = (size.height / (totalYMax - totalYMin))
        for (i in 0..(size.height.toInt())) {
            if (step > offset.y  && step < offset.y + ((size.height - 2.dp.toPx()) / scale) && text.toInt() % 5 == 0 && text.toInt() > totalYMin && text.toInt() < totalYMax) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${text.toInt()}",
                    offset.x + (textXOffset / scale),
                    step - (increment / 10f) + (5.dp.toPx() / scale),
                    textPaint.value
                )
                drawLine(
                    start = Offset(offset.x + ((size.width - 6.dp.toPx()) / scale), step),
                    end = Offset(offset.x + ((size.width + 20.dp.toPx()) / scale), step),
                    color = androidx.compose.ui.graphics.Color.Black,
                    strokeWidth = 3.dp.toPx() / scale,
                    alpha = 0.7f
                )
            }
            text += 1f
            step -= increment
        }
    }
}
