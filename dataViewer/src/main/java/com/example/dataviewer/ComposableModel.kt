package com.example.dataviewer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ComponentState
interface ComponentEvent
abstract class ComposableModel<TypeOfViewState : ComponentState, TypeOfViewEvent : ComponentEvent>
    : EventReceiver<TypeOfViewEvent>, StatePusher<TypeOfViewState> {
    val lastPushedState: TypeOfViewState? get() = viewState.value
    private var _viewState: MutableStateFlow<TypeOfViewState?> = MutableStateFlow(null)

    companion object {
        private const val DEBOUNCE_TIME_MS = 1000L
    }

    private var lastDebouncedTime: Long = Long.MIN_VALUE
    final override fun onEventDebounced(event: TypeOfViewEvent) {
        val currTime = System.currentTimeMillis()
        if (currTime > lastDebouncedTime + DEBOUNCE_TIME_MS) {
            lastDebouncedTime = currTime
            onEvent(event)
        }
    }

    final override val viewState: StateFlow<TypeOfViewState?> = _viewState
    final override fun TypeOfViewState.push() {
        pushState(this)
    }

    final override fun pushState(state: TypeOfViewState) {
        _viewState.value = state
    }
}
