package com.tlpcraft.kmp.demo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("barcode") val barcode: String,
    @SerialName("qrCode") val qrCode: String
)
