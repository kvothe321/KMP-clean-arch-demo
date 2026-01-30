package com.tlpcraft.kmp.demo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DimensionsDto(
    @SerialName("width") val width: Double,
    @SerialName("height") val height: Double,
    @SerialName("depth") val depth: Double
)
