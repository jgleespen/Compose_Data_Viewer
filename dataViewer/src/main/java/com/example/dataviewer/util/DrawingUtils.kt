package com.example.dataviewer.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect

fun DrawScope.guideCircles(
    offset: Offset,
    scale: Float,
    yBound: Float,
    pad: Float
) {
    drawCircle(Color.Blue, 20f / scale, Offset(offset.x + pad, offset.y + (size.height - yBound) - pad))
    drawCircle(Color.Red, 20f / scale, Offset(offset.x + (size.width / scale), offset.y + (size.height - yBound) - pad))
    drawCircle(Color.Yellow, 20f / scale, Offset(offset.x + pad, offset.y))
}
fun DrawScope.clipUntransformableRect(
    topLeft: Offset,
    bottomRight: Offset,
    content: DrawScope.() -> Unit
) {
    clipRect(
        top = topLeft.y,
        left = topLeft.x,
        bottom = bottomRight.y,
        right = bottomRight.x
    ) {
        content()
    }

}
