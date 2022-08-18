package com.example.composecharting.data.bundle

import com.example.composecharting.data.formatting.CoordinateFormatter

data class GraphData(
    val graphDataList: GraphDataList,
    val padding: Float,
    val coordinateFormatter: CoordinateFormatter,
)

