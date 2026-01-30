package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository
import com.tlpcraft.kmp.demo.domain.usecase.UseCase

data class GetProductsParams(val limit: Int, val skip: Int)

class GetProductsUseCase(private val productRepository: ProductRepository) : UseCase<GetProductsParams, Result<List<ProductPreview>>> {
    override suspend fun invoke(param: GetProductsParams): Result<List<ProductPreview>> = productRepository.getProducts(param.limit, param.skip)
}
