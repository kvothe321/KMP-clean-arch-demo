package com.tlpcraft.kmp.demo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("category") val category: String,
    @SerialName("price") val price: Float,
    @SerialName("discountPercentage") val discountPercentage: Double,
    @SerialName("rating") val rating: Double,
    @SerialName("stock") val stock: Int,
    @SerialName("tags") val tags: List<String>,
    @SerialName("brand") val brand: String? = null,
    @SerialName("sku") val sku: String,
    @SerialName("weight") val weight: Int,
    @SerialName("dimensions") val dimensions: DimensionsDto,
    @SerialName("warrantyInformation") val warrantyInformation: String,
    @SerialName("shippingInformation") val shippingInformation: String,
    @SerialName("availabilityStatus") val availabilityStatus: String,
    @SerialName("reviews") val reviews: List<ReviewDto>,
    @SerialName("returnPolicy") val returnPolicy: String,
    @SerialName("minimumOrderQuantity") val minimumOrderQuantity: Int,
    @SerialName("meta") val meta: MetaDto,
    @SerialName("images") val images: List<String>,
    @SerialName("thumbnail") val thumbnail: String
)
