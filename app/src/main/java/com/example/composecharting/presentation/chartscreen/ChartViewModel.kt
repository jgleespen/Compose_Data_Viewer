package com.example.composecharting.presentation.chartscreen

import androidx.lifecycle.viewModelScope
import com.example.composecharting.arch.Destination
import com.example.composecharting.arch.BaseRoutingViewModel
import com.example.composecharting.data.GetUnevenPairUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MainDestination : Destination

@HiltViewModel
class ChartViewModel
@Inject constructor(private val getUnevenDoubleListUseCase: GetUnevenPairUseCase) :
    BaseRoutingViewModel<ChartScreenViewState, ChartScreenViewEvent, MainDestination>() {
    init {
        viewModelScope.launch {
            val list = getUnevenDoubleListUseCase
        }
    }

    override fun onEvent(event: ChartScreenViewEvent) {

    }
}