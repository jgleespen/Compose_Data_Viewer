package com.company.strengthtracker.presentation.test_screen.graph_utils

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composecharting.data.bundle.GraphData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun XAxisLandscape(graphData: GraphData, colors: ColorScheme, scale: Float, offset: Offset) {
    val density = LocalDensity.current
    val xOffsetTemp_DELETE = 50f

/*
    val totalXMax = graphData.graphDataList.totalXMax.value
    val totalXMin = graphData.graphDataList.totalXMax.value
*/

    val textYOffset = offset.y + (35f / scale)
    //val xMax = remember { mutableStateOf(graphData.graphDataList.totalXMax.value)}
    //val xMin = remember { mutableStateOf(graphData.graphDataList.totalXMin.value)}

    val textPaint = remember { mutableStateOf(Paint()) }
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
        val width = size.width
        val xMax = graphData.graphDataList.totalXMax.value
        val xMin = graphData.graphDataList.totalXMin.value
        val increment: Float = (width - 50f) / (xMax - xMin)
        val rangeOfAxis: Int = ((width - 50f) / increment).toInt()
        textPaint.value.textSize /= scale

        var step = 50f
        var text = graphData.graphDataList.totalXMin.value
        //Log.d("XMAX??", "${xMax.toInt()}")
        //Log.d("INCREMENT", "${increment}")
        //Log.d("rangeOfaxis", "${rangeOfAxis}")

        for (i in 0..rangeOfAxis) {
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
            text += 1f
            step += increment
        }
    }
}
