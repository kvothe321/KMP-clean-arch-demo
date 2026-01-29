package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.repository.FavoriteProductRepository
import com.tlpcraft.kmp.demo.domain.usecase.UseCase

class RemoveFavoriteProductUseCase(
    private val favoriteProductRepository: FavoriteProductRepository
) : UseCase<Int, Unit> {
    override suspend fun invoke(param: Int) {
        favoriteProductRepository.removeFavoriteProduct(param)
    }
}
