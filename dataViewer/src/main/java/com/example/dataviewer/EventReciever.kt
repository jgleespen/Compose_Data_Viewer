package com.example.dataviewer

import androidx.lifecycle.ViewModel

internal interface EventReceiver<TypeOfViewEvent : ComponentEvent> {
    /** Invoked by the View and received by the implementing [ViewModel] **/
    fun onEvent(event: TypeOfViewEvent)

    fun onEventDebounced(event: TypeOfViewEvent)
}
