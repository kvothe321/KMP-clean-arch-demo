package com.tlpcraft.kmp.demo.data.mapper

import com.tlpcraft.kmp.demo.data.model.Product
import com.tlpcraft.kmp.demo.domain.model.ProductPreview

fun Product.toProductPreview() = ProductPreview(
    id = id,
    title = title,
    description = description,
    category = category,
    price = price
)
