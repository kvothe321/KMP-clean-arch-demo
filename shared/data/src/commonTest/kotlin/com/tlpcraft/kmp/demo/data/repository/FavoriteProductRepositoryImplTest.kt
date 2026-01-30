package com.tlpcraft.kmp.demo.data.repository

import com.tlpcraft.kmp.demo.data.datasource.local.FavoriteProductLocalDataSource
import com.tlpcraft.kmp.demo.data.datasource.remote.ProductRemoteDataSource
import com.tlpcraft.kmp.demo.data.shared.createProductDto
import com.tlpcraft.kmp.demo.domain.repository.FavoriteProductRepository
import com.tlpcraft.kmp.demo.domain.service.DispatcherProvider
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class FavoriteProductRepositoryImplTest {

    private lateinit var favoriteProductLocalDataSource: FavoriteProductLocalDataSource
    private lateinit var productRemoteDataSource: ProductRemoteDataSource
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var favoriteProductRepository: FavoriteProductRepository

    @BeforeTest
    fun setUp() {
        favoriteProductLocalDataSource = mock()
        productRemoteDataSource = mock()
        dispatcherProvider = mock()
        every { dispatcherProvider.io } returns Dispatchers.Unconfined
        favoriteProductRepository = FavoriteProductRepositoryImpl(
            dispatcherProvider,
            favoriteProductLocalDataSource,
            productRemoteDataSource
        )
    }

    @Test
    fun `getFavoriteProducts should return product previews on success`() = runTest {
        // Given
        val favoriteIds = listOf(1, 2)
        val productDto1 = createProductDto(1)
        val productDto2 = createProductDto(2)

        every { favoriteProductLocalDataSource.getFavoriteProductIds() } returns flowOf(favoriteIds)
        everySuspend { productRemoteDataSource.getProduct(1) } returns productDto1
        everySuspend { productRemoteDataSource.getProduct(2) } returns productDto2

        // When
        val result = favoriteProductRepository.getFavoriteProducts().first()

        // Then
        assertTrue(result.isSuccess)
        val productPreviews = result.getOrNull()
        assertEquals(2, productPreviews?.size)
        assertEquals("Test Product 1", productPreviews?.get(0)?.title)
        assertEquals("Test Product 2", productPreviews?.get(1)?.title)
    }

    @Test
    fun `getFavoriteProducts should handle partial failures`() = runTest {
        // Given
        val favoriteIds = listOf(1, 2)
        val productDto1 = createProductDto(1)

        every { favoriteProductLocalDataSource.getFavoriteProductIds() } returns flowOf(favoriteIds)
        everySuspend { productRemoteDataSource.getProduct(1) } returns productDto1
        everySuspend { productRemoteDataSource.getProduct(2) } throws RuntimeException("Product not found")

        // When
        val result = favoriteProductRepository.getFavoriteProducts().first()

        // Then
        assertTrue(result.isSuccess)
        val productPreviews = result.getOrNull()
        assertEquals(1, productPreviews?.size)
        assertEquals("Test Product 1", productPreviews?.get(0)?.title)
    }

    @Test
    fun `addFavoriteProduct should call local data source`() = runTest {
        // Given
        val productId = 1
        everySuspend { favoriteProductLocalDataSource.addFavoriteProduct(productId) } returns Unit

        // When
        favoriteProductRepository.addFavoriteProduct(productId)

        // Then
        verifySuspend { favoriteProductLocalDataSource.addFavoriteProduct(productId) }
    }

    @Test
    fun `removeFavoriteProduct should call local data source`() = runTest {
        // Given
        val productId = 1
        everySuspend { favoriteProductLocalDataSource.removeFavoriteProduct(productId) } returns Unit

        // When
        favoriteProductRepository.removeFavoriteProduct(productId)

        // Then
        verifySuspend { favoriteProductLocalDataSource.removeFavoriteProduct(productId) }
    }
}
