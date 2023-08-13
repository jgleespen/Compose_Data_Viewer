package com.example.dataviewer.modifier

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.dataviewer.linechart.LineChartColors
import com.example.dataviewer.linechart.LineChartViewEvent
import com.example.dataviewer.linechart.LineChartViewState.*
import com.example.dataviewer.util.clipUntransformableRect


fun Modifier.lineChartGestures(
    state: ChartLoaded,
    onGesture: (LineChartViewEvent.TransformationEvent) -> Unit,
): Modifier = state.run { ->
    pointerInput(Unit) {
        detectTransformGestures(
            onGesture = { centroid, dPan, dZoom, _ ->
                onGesture(
                    LineChartViewEvent.TransformationEvent(
                        centroid,
                        dPan,
                        dZoom,
                    )
                )
            }
        )
    }
        .graphicsLayer {
            state.run {
                translationX = (-offset.x * scale)
                translationY = (-offset.y * scale)
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin(0f, 0f)
            }
        }
}

