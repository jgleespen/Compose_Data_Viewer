package com.example.composecharting.data

import androidx.compose.ui.geometry.Offset
import javax.inject.Inject

class GetUnevenPairUseCase
@Inject constructor(private val dummyRepository: DummyRepository){
    suspend operator fun invoke(): List<List<Offset>> {
        return dummyRepository.getUnevenData()
    }
}