package com.example.composecharting.presentation.chartscreen

import androidx.lifecycle.viewModelScope
import com.example.composecharting.data.GetUnevenDoubleListUseCase
import com.example.mvvcmbase.models.Destination
import com.example.mvvcmbase.viewmodel.BaseRoutingViewModel
import com.example.mvvcmbase.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MainDestination : Destination

@HiltViewModel
class ChartViewModel
@Inject constructor(private val getUnevenDoubleListUseCase: GetUnevenDoubleListUseCase) :
    BaseRoutingViewModel<ChartScreenViewState, ChartScreenViewEvent, MainDestination>() {
    init {
        viewModelScope.launch {
            val list = getUnevenDoubleListUseCase
        }
    }

    override fun onEvent(event: ChartScreenViewEvent) {

    }
}