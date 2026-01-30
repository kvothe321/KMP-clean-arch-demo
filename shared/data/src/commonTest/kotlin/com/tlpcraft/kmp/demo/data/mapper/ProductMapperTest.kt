package com.tlpcraft.kmp.demo.data.mapper

import com.tlpcraft.kmp.demo.data.shared.createProductDto
import kotlin.test.Test
import kotlin.test.assertEquals

class ProductMapperTest {

    @Test
    fun `toProductPreview should map ProductDto to ProductPreview`() {
        // Given
        val productDto = createProductDto(1)

        // When
        val productPreview = productDto.toProductPreview()

        // Then
        assertEquals(productDto.id, productPreview.id)
        assertEquals(productDto.title, productPreview.title)
        assertEquals(productDto.description, productPreview.description)
        assertEquals(productDto.category, productPreview.category)
        assertEquals(productDto.price, productPreview.price)
    }

    @Test
    fun `toProduct should map ProductDto to Product`() {
        // Given
        val productDto = createProductDto(1)

        // When
        val product = productDto.toProduct()

        // Then
        assertEquals(productDto.id, product.id)
        assertEquals(productDto.title, product.title)
        assertEquals(productDto.description, product.description)
        assertEquals(productDto.category, product.category)
        assertEquals(productDto.price, product.price)
        assertEquals(productDto.discountPercentage, product.discountPercentage)
        assertEquals(productDto.rating, product.rating)
        assertEquals(productDto.stock, product.stock)
        assertEquals(productDto.tags, product.tags)
        assertEquals(productDto.brand, product.brand)
        assertEquals(productDto.sku, product.sku)
        assertEquals(productDto.weight, product.weight)
        assertEquals(productDto.warrantyInformation, product.warrantyInformation)
        assertEquals(productDto.shippingInformation, product.shippingInformation)
        assertEquals(productDto.availabilityStatus, product.availabilityStatus)
        assertEquals(productDto.returnPolicy, product.returnPolicy)
        assertEquals(productDto.minimumOrderQuantity, product.minimumOrderQuantity)
        assertEquals(productDto.images, product.images)
        assertEquals(productDto.thumbnail, product.thumbnail)
    }
}
