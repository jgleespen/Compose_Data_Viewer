package com.example.dataviewer.linechart

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.example.dataviewer.ComponentEvent
import com.example.dataviewer.ComponentState


data class LineChartColors(
    val ContainerColor: Color,
    val TitleColor: Color,
    val LineColor: Color,
    val AxisColor: Color
)


interface LineChartViewEvent : ComponentEvent {
    data class TransformationEvent(
        val centroid: Offset,
        val dPan: Offset,
        val dZoom: Float,
    ) : LineChartViewEvent
}

interface LineChartViewState : ComponentState {
    data class ChartLoaded(
        val data: List<List<Offset>>,
        val xBound: Float,
        val yBound: Float,
        val width: Float,
        val height: Float,
        val offset: Offset = Offset.Zero,
        val scale: Float = 1f
    ) : LineChartViewState
}
