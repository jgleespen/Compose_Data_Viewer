package com.example.mvvcmbase.viewmodel

import com.example.mvvcmbase.models.Destination
import com.example.mvvcmbase.models.ViewEvent
import com.example.mvvcmbase.models.ViewState

abstract class BaseRoutingViewModel<
        TypeOfViewState : ViewState,
        TypeOfViewEvent : ViewEvent,
        TypeOfDestination : Destination>
    : BaseViewModel<TypeOfViewState, TypeOfViewEvent>() {

    private var router: Router<TypeOfDestination>? = null

    fun attachRouter(router: Router<TypeOfDestination>) {
        this.router = router
        onRouterAttached()
    }

    fun routeTo(destination: TypeOfDestination) {
        this.router?.routeTo(destination)
    }

    /** Optional in implementing ViewModel **/
    open fun onRouterAttached() {}
}
