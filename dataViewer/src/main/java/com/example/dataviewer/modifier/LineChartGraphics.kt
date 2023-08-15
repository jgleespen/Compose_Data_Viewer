package com.example.dataviewer.modifier

import android.graphics.Paint
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.NativeCanvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.example.dataviewer.linechart.LineChartColors
import com.example.dataviewer.linechart.LineChartDefaults
import com.example.dataviewer.linechart.LineChartViewState.*
import com.example.dataviewer.util.clipUntransformableRect


fun Modifier.lineChartGraphics(
    state: ChartLoaded,
    textPaint: Paint,
    colors: LineChartColors
): Modifier = state.run {
    val pad = 100f / scale
    drawBehind {
        drawAxis(
            data,
            offset,
            scale,
            yBound,
            pad,
            textPaint,
            colors.AxisColor
        )

        /*drawChartView(
            offset,
            scale,
            yBound,
            pad
        ) {
        }*/

        drawData(state.data, colors)
    }
}

private fun DrawScope.drawChartView(
    offset: Offset,
    scale: Float,
    yBound: Float,
    pad: Float,
    content: DrawScope.() -> Unit
) {
    clipUntransformableRect(
        topLeft = Offset(offset.x + pad, offset.y),
        bottomRight = Offset(offset.x + (size.width / scale), offset.y + (size.height - yBound) - pad),
        content = content
    )
}

private fun DrawScope.drawData(data: List<List<Offset>>, colors: LineChartColors) {

    data.forEach {
        val path = Path().apply {
            it.forEachIndexed { i, offset ->
                when (i) {
                    0 -> moveTo(offset.x, offset.y)
                    else -> lineTo(offset.x, offset.y)
                }
            }
        }
        drawPath(path, colors.AxisColor, style = Stroke(width = 10f))
    }
}

/* Replace [data] with some list of axis values from VM */
private fun DrawScope.drawAxis(
    data: List<List<Offset>>,
    offset: Offset,
    scale: Float,
    yBound: Float,
    pad: Float,
    textPaint: Paint,
    color: Color
) {
    drawLine(
        start = Offset(
            offset.x + pad,
            offset.y
        ),
        end = Offset(
            offset.x + pad,
            offset.y + (size.height / scale) - (pad - (2.5.dp.toPx() / scale))
        ),
        color = color,
        strokeWidth = 5.dp.toPx() / scale
    )
    drawLine(
        start = Offset(
            x = offset.x + pad,
            y = offset.y + (size.height / scale) - pad
        ),
        end = Offset(
            x = offset.x + (size.width / scale),
            y = offset.y + (size.height / scale) - pad
        ),
        color = color,
        strokeWidth = 5.dp.toPx() / scale
    )
    this.drawContext.canvas.nativeCanvas.drawAxisValues(
        data = data,
        offset = offset,
        scale = scale,
        pad = pad,
        textPaint = textPaint
    )
}

fun NativeCanvas.drawAxisValues(
    data: List<List<Offset>>,
    offset: Offset,
    scale: Float,
    pad: Float,
    textPaint: Paint
) {

}
