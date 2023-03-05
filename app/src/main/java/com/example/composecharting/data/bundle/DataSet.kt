package com.example.composecharting.data.bundle

import android.util.Log

class DataSet(
    val coordinateArray: Array<MutableList<Float>>,
) {
    val xArr = coordinateArray[0]
    val yArr = coordinateArray[1]
    var xMax = Float.MIN_VALUE
    var xMin = Float.MAX_VALUE
    var yMax = Float.MIN_VALUE
    var yMin = Float.MAX_VALUE

    fun getLastX(): Float {
        return coordinateArray.first().last()
    }
    fun addLastX(element: Float) {
        coordinateArray.apply {
            first().add(element)
        }
    }

    fun init() {
        coordinateArray[0].forEach { x ->
            xMax = maxOf(x, xMax)
            xMin = minOf(x, xMin)
        }
        coordinateArray[1].forEach { y ->
            yMax = maxOf(y, yMax)
            yMin = minOf(y, yMin)
        }
    }
}