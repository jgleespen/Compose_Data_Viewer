package com.example.composecharting

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composecharting.arch.Router
import com.example.composecharting.presentation.chartscreen.ChartViewDelegate
import com.example.composecharting.presentation.chartscreen.ChartViewModel
import com.example.composecharting.presentation.chartscreen.MainDestination
import com.example.composecharting.presentation.testScreen.TestScreen
import com.example.composecharting.ui.theme.ComposeChartingTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

interface MainDestination {

}
@AndroidEntryPoint
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity(), Router<MainDestination> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeChartingTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.TestScreen.route
                ){
                    composable(route = Screen.TestScreen.route){
                        TestScreen(navController)
                    }
                    composable(route = Screen.ChartScreen.route) {
                        ChartViewDelegate(viewModel = hiltViewModel<ChartViewModel>().apply {
                            attachRouter(router = this@MainActivity)
                        })
                    }
                }
            }
        }
    }

    override fun routeTo(destination: MainDestination) {
        TODO("Not yet implemented")
    }
}

