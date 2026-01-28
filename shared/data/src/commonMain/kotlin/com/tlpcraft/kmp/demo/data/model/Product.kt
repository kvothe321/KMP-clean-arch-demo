package com.tlpcraft.kmp.demo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("category") val category: String,
    @SerialName("price") val price: Float,
    @SerialName("discountPercentage") val discountPercentage: Float,
    @SerialName("rating") val rating: Float,
    @SerialName("stock") val stock: Int
)
