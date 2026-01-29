package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.domain.repository.FavoriteProductRepository
import com.tlpcraft.kmp.demo.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow

class GetFavoriteProductsUseCase(
    private val favoriteProductRepository: FavoriteProductRepository
) : UseCase<Unit, Flow<Result<List<ProductPreview>>>> {
    override suspend fun invoke(param: Unit): Flow<Result<List<ProductPreview>>> = favoriteProductRepository.getFavoriteProducts()
}
