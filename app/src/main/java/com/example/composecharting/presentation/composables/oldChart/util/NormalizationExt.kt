package com.example.composecharting.presentation.composables.oldChart.util

import android.util.Range


fun Float.normalize(
    currentMin: Float,
    currentMax: Float,
    newMin: Float,
    newMax: Float
): Float = (((this - currentMin) / (currentMax - currentMin)) * (newMax - newMin)) + newMin

fun Double.normalize(
    currentMin: Double,
    currentMax: Double,
    newMin: Double,
    newMax: Double
): Double = (((this - currentMin) / (currentMax - currentMin)) * (newMax - newMin)) + newMin