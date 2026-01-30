package com.tlpcraft.kmp.demo.data.mapper

import com.tlpcraft.kmp.demo.data.model.ProductDto
import com.tlpcraft.kmp.demo.domain.model.Product
import com.tlpcraft.kmp.demo.domain.model.ProductPreview

fun ProductDto.toProductPreview() = ProductPreview(
    id = id,
    title = title,
    description = description,
    category = category,
    price = price
)

fun ProductDto.toProduct() = Product(
    id = id,
    title = title,
    description = description,
    category = category,
    price = price,
    discountPercentage = discountPercentage,
    rating = rating,
    stock = stock,
    tags = tags,
    brand = brand,
    sku = sku,
    weight = weight,
    warrantyInformation = warrantyInformation,
    shippingInformation = shippingInformation,
    availabilityStatus = availabilityStatus,
    returnPolicy = returnPolicy,
    minimumOrderQuantity = minimumOrderQuantity,
    images = images,
    thumbnail = thumbnail
)
