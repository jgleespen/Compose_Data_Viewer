package com.example.mvvcmbase.viewmodel

import com.example.mvvcmbase.models.Destination

interface Router<TypeOfDestination: Destination> {
    fun routeTo(destination: TypeOfDestination)
}
