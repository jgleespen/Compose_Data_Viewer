package com.example.composecharting.data

import androidx.compose.ui.geometry.Offset

class DummyRepository{
    suspend fun getUnevenDoubleList(): List<List<Offset>> {
        return listOf(
            listOf(
                Offset(5f, 50f),
                Offset(10f, 30f),
                Offset(25f, 40f),
                Offset(35f, 35f),
                Offset(50f, 40f)
            ),
            listOf(
                Offset(1f, 60f),
                Offset(15f, 62.5f),
                Offset(30f, 50f)
            )
        )
    }
}

class GetUnevenDoubleListUseCase() {
    suspend operator fun invoke() {
        DummyRepository().getUnevenDoubleList()
    }
}