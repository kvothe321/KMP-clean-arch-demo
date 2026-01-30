package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.Product
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class GetProductDetailsUseCaseTest : KoinTest {

    private val productRepository: ProductRepository = mock()
    private val getProductDetailsUseCase: GetProductDetailsUseCase by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { productRepository }
                    factory { GetProductDetailsUseCase(get()) }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `invoke should return product details on success`() = runTest {
        // Given
        val productId = 1
        val product = Product(
            id = productId,
            title = "Test Product",
            description = "Test Description",
            price = 100.0f,
            discountPercentage = 10.0,
            rating = 4.5,
            stock = 10,
            brand = "Test Brand",
            category = "Test Category",
            thumbnail = "",
            images = emptyList(),
            tags = emptyList(),
            sku = "TEST-SKU",
            weight = 1,
            warrantyInformation = "1 year",
            shippingInformation = "Ships in 1-2 days",
            availabilityStatus = "In Stock",
            returnPolicy = "30 day return",
            minimumOrderQuantity = 1
        )
        everySuspend { productRepository.getProduct(productId) } returns Result.success(product)

        // When
        val result = getProductDetailsUseCase(productId)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(product, result.getOrNull())
        verifySuspend { productRepository.getProduct(productId) }
    }

    @Test
    fun `invoke should return failure when repository fails`() = runTest {
        // Given
        val productId = 1
        val exception = RuntimeException("Network error")
        everySuspend { productRepository.getProduct(productId) } returns Result.failure(exception)

        // When
        val result = getProductDetailsUseCase(productId)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verifySuspend { productRepository.getProduct(productId) }
    }
}
