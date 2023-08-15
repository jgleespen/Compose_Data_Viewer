package com.example.base_app_example

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.example.base_app_example.ui.theme.Base_app_exampleTheme
import com.example.dataviewer.linechart.LineChart
import com.example.dataviewer.linechart.rememberLineChartState
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = setupData()
        setContent {
            Base_app_exampleTheme {
                BoxWithConstraints(Modifier.fillMaxSize()) {
                    val h = constraints.maxHeight.toFloat()
                    val w = constraints.maxWidth.toFloat()
                    println("h: $h")
                    println("w: $w")
                    val lineChartState = rememberLineChartState(
                        //data = listOf(listOf(Offset(1f, 45f), Offset(1.5f, 58f), Offset(2f, 50f), Offset(10f, 25f))),
                        data = data,
                        height = LocalDensity.current.run { maxHeight.toPx() },
                        width = LocalDensity.current.run { maxWidth.toPx() }
                    )
                    LineChart(modifier = Modifier.fillMaxSize().align(Alignment.Center), state = lineChartState, title = { })
                }



/*
                Surface() {

                    data.forEach {
                        it.forEach { of ->
                            Log.e("", "(${of.x}, ${of.y})  ")
                        }
                        Log.d("", "\n")
                    }
                }
*/

            }
        }
    }
}

fun setupData(): List<List<Offset>> {
    val min = 20f
    val max = 250f
    val numberOfLists = 3
    val rand = Random;
    return List(numberOfLists) {
        getOffsetList(10, min, max)
    }
}

fun getOffsetList(size: Int, min: Float, max: Float): List<Offset> {
    val listY = List(size) { Random.nextFloat() * (max - min) + min }
    val listX = getRandomXList(size, 1, 10)

    return listX.zip(listY) { x, y -> Offset(x, y) }
}

fun getRandomXList(size: Int, min: Int, max: Int): List<Float> {
    var list = mutableListOf<Float>()
    var c = min.toFloat();
    val range = Random.nextInt(size - 4, size)
    repeat(range) {
        val d = 1 + Random.nextFloat()
        if(c + d < max)
            list.add(c + d)
        c += d
        println(c)
    }
    return list
}

fun getRange(min: Float, max: Float): List<Float> = List((max - min).toInt()) { it + min }
