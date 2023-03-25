package com.example.composecharting

sealed class Screen(val route:String){
    object TestScreen: Screen("test_screen")
    object ChartScreen: Screen("chart_screen")
}