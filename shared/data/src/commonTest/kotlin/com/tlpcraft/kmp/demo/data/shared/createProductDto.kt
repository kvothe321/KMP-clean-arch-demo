package com.tlpcraft.kmp.demo.data.shared

import com.tlpcraft.kmp.demo.data.model.DimensionsDto
import com.tlpcraft.kmp.demo.data.model.MetaDto
import com.tlpcraft.kmp.demo.data.model.ProductDto

fun createProductDto(id: Int) = ProductDto(
    id = id,
    title = "Test Product $id",
    description = "Description $id",
    category = "Category",
    price = 100.0f,
    discountPercentage = 10.0,
    rating = 4.5,
    stock = 10,
    tags = emptyList(),
    brand = "Brand",
    sku = "SKU$id",
    weight = 1,
    dimensions = DimensionsDto(0.0, 0.0, 0.0),
    warrantyInformation = "1 year",
    shippingInformation = "Ships fast",
    availabilityStatus = "In Stock",
    reviews = emptyList(),
    returnPolicy = "30 days",
    minimumOrderQuantity = 1,
    meta = MetaDto("", "", "", ""),
    images = emptyList(),
    thumbnail = ""
)
