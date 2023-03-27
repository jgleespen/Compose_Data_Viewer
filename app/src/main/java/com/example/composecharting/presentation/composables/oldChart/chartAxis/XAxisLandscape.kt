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
import androidx.compose.ui.unit.sp
import com.example.composecharting.presentation.composables.oldChart.util.GraphData

@Composable
fun XAxisLandscape(graphData: GraphData, colors: ColorScheme, scale: Float, offset: Offset) {
    val density = LocalDensity.current

/*
    val totalXMax = graphData.graphDataList.totalXMax.value
    val totalXMin = graphData.graphDataList.totalXMax.value
*/

    val textYOffset = offset.y  + (35f / scale)
/*
    val xMax = remember { mutableStateOf(graphData.graphDataList.totalXMax.value)}
    val xMin = remember { mutableStateOf(graphData.graphDataList.totalXMin.value)}
*/
    val axisPadding = 0f
    val xMax = graphData.graphDataList.totalXMax.value + axisPadding
    val xMin = graphData.graphDataList.totalXMin.value - axisPadding

    val textPaint = remember { mutableStateOf(Paint())}
     textPaint.value =
            Paint().apply {
                color = Color.BLACK
                textAlign = Paint.Align.LEFT
                textSize = density.run { 12.sp.toPx() }
            }
    textPaint.value.isAntiAlias = true
    textPaint.value.isLinearText = true
    Canvas(modifier = Modifier
        .fillMaxSize()
        .clipToBounds()
        .background(color = colors.surface)
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
            translationX = -offset.x * scale
            translationY = -offset.y * scale
            transformOrigin = TransformOrigin(0f, 0f)
        }
    ) {
        val width = size.width - 10.dp.toPx()
        val maxY = size.height * (scale - 1f) / scale
        val maxX = width * (scale - 1f) / scale
        textPaint.value.textSize /= scale

        val increment = (width) / (xMax - xMin) * 5f

        var step = 0f
        var text = xMin


        for (i in 0..xMax.toInt()) {
            if(text.toInt() > 0f && text.toInt() % 1 == 0 && step <= offset.x + ((width  + 5.dp.toPx()) / scale)) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${text.toInt()}",
                    step - (3.dp.toPx() / scale),
                    textYOffset + (10.dp.toPx() / scale),
                    textPaint.value
                )
                drawLine(
                    start = Offset(step, offset.y + (6.dp.toPx() / scale)),
                    end = Offset(step, offset.y),
                    color = androidx.compose.ui.graphics.Color.Black,
                    strokeWidth = 3.dp.toPx() / scale,
                    alpha = 0.7f
                )
            }
            text += 5f
            step +=  increment
        }
    }
}
