package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.repository.FavoriteProductRepository
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AddFavoriteProductUseCaseTest : KoinTest {

    private val favoriteProductRepository: FavoriteProductRepository = mock()
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase by inject()

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                module {
                    single { favoriteProductRepository }
                    factory { AddFavoriteProductUseCase(get()) }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `invoke should call addFavoriteProduct on repository`() = runTest {
        // Given
        val productId = 123
        everySuspend { favoriteProductRepository.addFavoriteProduct(productId) } returns Unit

        // When
        addFavoriteProductUseCase(productId)

        // Then
        verifySuspend { favoriteProductRepository.addFavoriteProduct(productId) }
    }
}
