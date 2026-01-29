package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository
import com.tlpcraft.kmp.demo.domain.usecase.UseCase

data class SearchProductsParams(val query: String, val limit: Int, val skip: Int)

class SearchProductsUseCase(private val productRepository: ProductRepository) : UseCase<SearchProductsParams, Result<List<ProductPreview>>> {
    override suspend fun invoke(param: SearchProductsParams): Result<List<ProductPreview>> =
        productRepository.searchProducts(param.query, param.limit, param.skip)
}
