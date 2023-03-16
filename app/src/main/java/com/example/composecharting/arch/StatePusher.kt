package com.example.mvvcmbase.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvcmbase.models.ViewEvent
import com.example.mvvcmbase.models.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface StatePusher<TypeOfViewState : ViewState> {
    /** State flow observed in View, triggers recomposition on each pushed [ViewState] **/
    val viewState: StateFlow<TypeOfViewState?>

    /** Abstraction to update underlying [MutableStateFlow] instance of [viewState]
     * from the implementing ViewModel **/
    fun pushState(state: TypeOfViewState)

    /** Optional extension to push a [ViewState] in the implementing [ViewModel] scope **/
    fun TypeOfViewState.push()
}
