package com.example.composecharting.arch

interface Router<TypeOfDestination: Destination> {
    fun routeTo(destination: TypeOfDestination)
}
