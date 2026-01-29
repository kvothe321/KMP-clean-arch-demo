package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.domain.repository.FavoriteProductRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteProductsUseCase(
    private val favoriteProductRepository: FavoriteProductRepository
) {
    operator fun invoke(): Flow<Result<List<ProductPreview>>> = favoriteProductRepository.getFavoriteProducts()
}
