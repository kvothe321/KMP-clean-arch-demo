package com.tlpcraft.kmp.demo.data.networking.datasource

import com.tlpcraft.kmp.demo.data.datasource.remote.ProductRemoteDataSource
import com.tlpcraft.kmp.demo.data.networking.api.ProductApi
import com.tlpcraft.kmp.demo.data.shared.createProductDto
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class ProductRemoteDataSourceImplTest : KoinTest {

    private val productApi: ProductApi = mock()
    private val productRemoteDataSource: ProductRemoteDataSource by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { productApi }
                    factory<ProductRemoteDataSource> { ProductRemoteDataSourceImpl(get()) }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `getProducts should call api and return products`() = runTest {
        // Given
        val limit = 10
        val skip = 0
        val expectedProducts = listOf(createProductDto(1))
        everySuspend { productApi.getProducts(limit, skip) } returns expectedProducts

        // When
        val result = productRemoteDataSource.getProducts(limit, skip)

        // Then
        assertEquals(expectedProducts, result)
        verifySuspend { productApi.getProducts(limit, skip) }
    }

    @Test
    fun `searchProducts should call api and return products`() = runTest {
        // Given
        val query = "phone"
        val limit = 5
        val skip = 0
        val expectedProducts = listOf(createProductDto(1))
        everySuspend { productApi.searchProducts(query, limit, skip) } returns expectedProducts

        // When
        val result = productRemoteDataSource.searchProducts(query, limit, skip)

        // Then
        assertEquals(expectedProducts, result)
        verifySuspend { productApi.searchProducts(query, limit, skip) }
    }

    @Test
    fun `getProduct should call api and return product`() = runTest {
        // Given
        val productId = 1
        val expectedProduct = createProductDto(productId)
        everySuspend { productApi.getProduct(productId) } returns expectedProduct

        // When
        val result = productRemoteDataSource.getProduct(productId)

        // Then
        assertEquals(expectedProduct, result)
        verifySuspend { productApi.getProduct(productId) }
    }
}
