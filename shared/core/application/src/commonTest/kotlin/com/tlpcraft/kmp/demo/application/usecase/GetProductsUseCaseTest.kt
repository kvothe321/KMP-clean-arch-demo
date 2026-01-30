package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.ProductPreview
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

class GetProductsUseCaseTest : KoinTest {

    private val productRepository: ProductRepository = mock()
    private val getProductsUseCase: GetProductsUseCase by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { productRepository }
                    factory { GetProductsUseCase(get()) }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `invoke should return product list on success`() = runTest {
        // Given
        val params = GetProductsParams(limit = 10, skip = 0)
        val productPreviews = listOf(
            ProductPreview(
                id = 1,
                title = "Test Product 1",
                description = "Test Description",
                category = "Test Category",
                price = 100.0f
            )
        )
        everySuspend { productRepository.getProducts(params.limit, params.skip) } returns Result.success(productPreviews)

        // When
        val result = getProductsUseCase(params)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(productPreviews, result.getOrNull())
        verifySuspend { productRepository.getProducts(params.limit, params.skip) }
    }

    @Test
    fun `invoke should return failure when repository fails`() = runTest {
        // Given
        val params = GetProductsParams(limit = 10, skip = 0)
        val exception = RuntimeException("Network error")
        everySuspend { productRepository.getProducts(params.limit, params.skip) } returns Result.failure(exception)

        // When
        val result = getProductsUseCase(params)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        verifySuspend { productRepository.getProducts(params.limit, params.skip) }
    }
}
