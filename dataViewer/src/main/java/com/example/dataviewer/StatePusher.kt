package com.example.dataviewer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal interface StatePusher<TypeOfViewState : ComponentState> {
    /** State flow observed in View, triggers recomposition on each pushed [ComponentState] **/
    val viewState: StateFlow<TypeOfViewState?>

    /** Abstraction to update underlying [MutableStateFlow] instance of [viewState]
     * from the implementing ViewModel **/
    fun pushState(state: TypeOfViewState)

    /** Optional extension to push a [ComponentState] in the implementing [ViewModel] scope **/
    fun TypeOfViewState.push()
}
