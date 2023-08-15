package com.example.dataviewer.linechart

import android.graphics.Paint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dataviewer.EventReceiver
import com.example.dataviewer.linechart.LineChartViewState.*
import com.example.dataviewer.modifier.lineChartGestures
import com.example.dataviewer.modifier.lineChartGraphics

@Composable
fun LineChart(
    state: LineChartState,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit),
) {
    state.viewState.collectAsState().value?.apply {
        when (this) {
            is ChartLoaded -> LineChart(modifier, this, title, state)
        }
    }
}

@Composable
private fun LineChart(
    modifier: Modifier,
    state: ChartLoaded,
    title: @Composable () -> Unit,
    eventReceiver: EventReceiver<LineChartViewEvent>,
) {
    ChartContainer(
        modifier = modifier
            .lineChartGestures(
                state = state,
                eventReceiver::onEvent
            )
            .lineChartGraphics(
                state,
                LineChartDefaults.axisTextStyle(),
                LineChartDefaults.colors()
            ),
    ) {
        title.invoke()
    }
}

@Composable
fun ChartContainer(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .sizeIn(minWidth = LineChartDefaults.ContainerWidth)
    ) {
        content()
    }
}





@Stable
object LineChartDefaults {
    val ContainerWidth: Dp = 400.dp

    @Composable
    fun axisTextStyle(): Paint = Paint().apply {
        color = MaterialTheme.colorScheme.onSurface.value.toInt()
        textAlign = Paint.Align.LEFT
        textSize = LocalDensity.current.run { 12.sp.toPx() }
        isAntiAlias = true
        isLinearText = true
    }

    @Composable
    fun colors(): LineChartColors = LineChartColors(
        ContainerColor = MaterialTheme.colorScheme.surface,
        TitleColor = MaterialTheme.colorScheme.onSurface,
        LineColor = MaterialTheme.colorScheme.onSurfaceVariant,
        AxisColor = MaterialTheme.colorScheme.onSurface
    )
}

