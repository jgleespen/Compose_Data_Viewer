package com.example.dataviewer.linechart

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import com.example.dataviewer.ComposableViewModel

@Composable
@ExperimentalMaterial3Api
fun rememberLineChartState(
    data: List<List<Offset>>,
    height: Float,
    width: Float,
): LineChartState = remember {
    LineChartState(data, height, width)
}

class LineChartState(
    data: List<List<Offset>>,
    private val height: Float,
    private val width: Float,
) : ComposableViewModel<LineChartViewState, LineChartViewEvent>() {
    private val yMax: Float = data.map { it.maxBy(Offset::y) }.maxBy(Offset::y).y
    private val yMin: Float = data.map { it.minBy(Offset::y) }.minBy(Offset::y).y
    private val xMax: Float = data.map { it.maxBy(Offset::x) }.maxBy(Offset::x).x
    private val xMin: Float = data.map { it.minBy(Offset::x) }.minBy(Offset::x).x
    private val viewPadding: Float = 100f + maxOf((yMax - yMin), (xMax - xMin))//must equal starting pad on axis
    private val normalizedData: List<List<Offset>> = data.map { coordinates ->
        coordinates.map {
            println(viewPadding)
            Offset(
                x = ((width - viewPadding) * (it.x - xMin) / (xMax - xMin)) + viewPadding,
                y = (height - viewPadding) * (yMax - it.y) / (yMax - yMin)
            )
        }
    }

    init {
        LineChartViewState.ChartLoaded(
            data = normalizedData,
            height = height,
            width = width,
            xBound = calculateBound(width, SCALE_LOWER_BOUND),
            yBound = calculateBound(height, SCALE_LOWER_BOUND)
        ).push()
    }

    private fun applyPanZoomGesture(
        transformation: LineChartViewEvent.TransformationEvent
    ) {
        (lastPushedState as? LineChartViewState.ChartLoaded)?.run {
            transformation.run {
                val newScale = (scale * dZoom)
                    .coerceIn(
                        SCALE_LOWER_BOUND,
                        SCALE_TOP_BOUND
                    )
                copy(
                    offset = calculateNewOffset(
                        transformation = this,
                        offset = offset,
                        currentScale = scale,
                        newScale,

                    ),
                    xBound = calculateBound(width, newScale),
                    yBound = calculateBound(height, newScale),
                    scale = newScale
                )
            }.push()
        }
    }

    private fun calculateNewOffset(
        transformation: LineChartViewEvent.TransformationEvent,
        offset: Offset,
        currentScale: Float,
        newScale: Float
    ): Offset = transformation.run {
        (offset + (centroid / currentScale) - ((centroid / newScale) + (dPan / newScale))).coerceOffsetInBounds(newScale)
    }
    private fun Offset.coerceOffsetInBounds(
        scale: Float
    ): Offset =
        Offset(
            this.x.coerceIn(PAN_LOWER_BOUND, calculateBound(width, scale)),
            this.y.coerceIn(PAN_LOWER_BOUND, calculateBound(height, scale))
        )

    private fun calculateBound(dimension: Float, scale: Float): Float = dimension * (scale - 1f) / scale


    fun applyPanGesture(
        dPan: Offset
    ) {
    }


    companion object {
        private const val SCALE_TOP_BOUND = 3f
        private const val SCALE_LOWER_BOUND = 1f
        private const val PAN_LOWER_BOUND = 0f
    }

    override fun onEvent(event: LineChartViewEvent) {
        when (event) {
            is LineChartViewEvent.TransformationEvent -> applyPanZoomGesture(event)
            // add more cases for other types of LineChartViewEvent as needed
        }
    }
}
