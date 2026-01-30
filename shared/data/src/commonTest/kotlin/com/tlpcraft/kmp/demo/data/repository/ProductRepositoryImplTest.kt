package com.tlpcraft.kmp.demo.data.repository

import com.tlpcraft.kmp.demo.data.datasource.remote.ProductRemoteDataSource
import com.tlpcraft.kmp.demo.data.mapper.toProduct
import com.tlpcraft.kmp.demo.data.mapper.toProductPreview
import com.tlpcraft.kmp.demo.data.shared.createProductDto
import com.tlpcraft.kmp.demo.domain.service.DispatcherProvider
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest

class ProductRepositoryImplTest {

    private val testDispatcher = Dispatchers.Unconfined
    private val dispatcherProvider: DispatcherProvider = mock<DispatcherProvider>().also {
        every { it.io } returns testDispatcher
    }
    private val productRemoteDataSource: ProductRemoteDataSource = mock()

    private val productRepository = ProductRepositoryImpl(
        dispatcherProvider = dispatcherProvider,
        productRemoteDataSource = productRemoteDataSource
    )

    private val productDto = createProductDto(123)

    @Test
    fun `getProducts should return product previews on success`() = runTest {
        // Given
        val limit = 10
        val skip = 0
        val productDtos = listOf(productDto)
        val expectedProductPreviews = productDtos.map { it.toProductPreview() }
        everySuspend { productRemoteDataSource.getProducts(limit, skip) } returns productDtos

        // When
        val result = productRepository.getProducts(limit, skip)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedProductPreviews, result.getOrNull())
        verifySuspend { productRemoteDataSource.getProducts(limit, skip) }
    }

    @Test
    fun `getProducts should return failure when remote source fails`() = runTest {
        // Given
        val limit = 10
        val skip = 0
        val exception = RuntimeException("Network Error")
        everySuspend { productRemoteDataSource.getProducts(limit, skip) } throws exception

        // When
        val result = productRepository.getProducts(limit, skip)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verifySuspend { productRemoteDataSource.getProducts(limit, skip) }
    }

    @Test
    fun `searchProducts should return product previews on success`() = runTest {
        // Given
        val query = "test"
        val limit = 10
        val skip = 0
        val productDtos = listOf(productDto)
        val expectedProductPreviews = productDtos.map { it.toProductPreview() }
        everySuspend { productRemoteDataSource.searchProducts(query, limit, skip) } returns productDtos

        // When
        val result = productRepository.searchProducts(query, limit, skip)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedProductPreviews, result.getOrNull())
        verifySuspend { productRemoteDataSource.searchProducts(query, limit, skip) }
    }

    @Test
    fun `searchProducts should return failure when remote source fails`() = runTest {
        // Given
        val query = "test"
        val limit = 10
        val skip = 0
        val exception = RuntimeException("Network Error")
        everySuspend { productRemoteDataSource.searchProducts(query, limit, skip) } throws exception

        // When
        val result = productRepository.searchProducts(query, limit, skip)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verifySuspend { productRemoteDataSource.searchProducts(query, limit, skip) }
    }

    @Test
    fun `getProduct should return product on success`() = runTest {
        // Given
        val productId = 1
        val expectedProduct = productDto.toProduct()
        everySuspend { productRemoteDataSource.getProduct(productId) } returns productDto

        // When
        val result = productRepository.getProduct(productId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedProduct, result.getOrNull())
        verifySuspend { productRemoteDataSource.getProduct(productId) }
    }

    @Test
    fun `getProduct should return failure when remote source fails`() = runTest {
        // Given
        val productId = 1
        val exception = RuntimeException("Network Error")
        everySuspend { productRemoteDataSource.getProduct(productId) } throws exception

        // When
        val result = productRepository.getProduct(productId)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verifySuspend { productRemoteDataSource.getProduct(productId) }
    }
}
