package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository
import com.tlpcraft.kmp.demo.domain.usecase.UseCase

class GetProductsUseCase(private val productRepository: ProductRepository) : UseCase<Int, Result<List<ProductPreview>>> {

    override suspend fun invoke(param: Int): Result<List<ProductPreview>> = productRepository.getProducts(param)
}
