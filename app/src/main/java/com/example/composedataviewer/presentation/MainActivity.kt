package com.example.composedataviewer.presentation

import android.os.Bundle
import android.util.Range
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import com.example.composedataviewer.presentation.theme.*
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = setupData()
        setContent {
            ExampleApplicationTheme {
                // A surface container using the 'background' color from the theme

                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    data.forEach {
                        Row(modifier = Modifier.fillMaxSize()) {
                            it.forEach {
                                Text(text = "${it.x} : ${it.y}")
                            }
                        }
                    }
                    //val state = rememberLineChartState(data = data, height = minHeight.value, width = minWidth.value)
                }
            }
        }
    }
}


fun setupData(): List<List<Offset>> {
    val min = 20f
    val max = 250f
    val numberOfLists = 3

    return listOf(
        getOffsetList(5, min, max),
        getOffsetList(9, min, max),
        getOffsetList(7, min, max)
    )
}

fun getOffsetList(size: Int, min: Float, max: Float): List<Offset> {
    val listY = List(size) { Random.nextFloat() * (max - min) + min }
    val listX = getRange(0f, size.toFloat())

    return listX.zip(listY) { x, y -> Offset(x, y) }
}

fun getRange(min: Float, max: Float): List<Float> = List((max - min).toInt()) { it + min }


