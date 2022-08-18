package com.example.composecharting.presentation.testScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.composecharting.data.bundle.DataSet
import com.example.composecharting.data.bundle.GraphDataList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//test viewModel for holding input data for different graphs Im working on
@HiltViewModel
class TestViewModel @Inject constructor() : ViewModel() {
    /*
    * r_min: min of the range of measurement
    * r_max: max of the range of measurement
    * t_min: min of the range of target scaling
    * t_max: max of the range of target scaling
    * m:
    * */
    //============TEST VALUES

    var x1: MutableList<Float> = mutableListOf(
        1f, 2f, 3f, 4f, 5f
    )
    val y1: MutableList<Float> = mutableListOf(
        50f, 45f, 40f, 35f, 40f
    )
    var x2: MutableList<Float> = mutableListOf(
        1f, 3f, 4f, 4.5f, 5f
    )
    val y2: MutableList<Float> = mutableListOf(
        60f, 62.5f, 65f, 65f, 63f
    )
    var a = DataSet(
        coordinateArray = arrayOf(x1, y1)
    )
    var b = DataSet(
        coordinateArray = arrayOf(x2, y2)
    )



    var dataList = mutableStateOf(GraphDataList(coordinates = mutableListOf(
        b, a
    )))
    init {
        a.init()
        b.init()
            dataList.value = GraphDataList(
                coordinates = mutableListOf(
                    a,
                    b
                )
            )
    }

//=========\\TEST VALUES

    /*normalizes xList to larger of two ranges --> this probably doesnt work but want to check*/
    fun normalize(xList: MutableList<Float>, xListb: MutableList<Float>): MutableList<Float> {
        val range = Math.max(max(xList) - min(xList), max(xListb) - min(xListb))
        println(range)
        val tempXList: MutableList<Float> = mutableListOf()
        val tempXListb: MutableList<Float> = mutableListOf()
        if (range != null) {
            xList.forEachIndexed { i, it ->
                tempXList.add((it - min(xList)) / range)
            }
            xListb.forEachIndexed { i, it ->
                tempXListb.add((it - min(xListb)) / range)
            }
        }
        return tempXList
    }

    fun max(list: MutableList<Float>): Float {
        var t = Float.MIN_VALUE
        list.forEach {
            if (t < it)
                t = it
        }
        return t
    }

    fun min(list: MutableList<Float>): Float {
        var t = Float.MAX_VALUE
        list.forEach {
            if (t > it)
                t = it
        }
        return t
    }


}
