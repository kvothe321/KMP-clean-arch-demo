package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.Product
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository
import com.tlpcraft.kmp.demo.domain.usecase.UseCase

class GetProductDetailsUseCase(private val productRepository: ProductRepository) : UseCase<Int, Result<Product>> {
    override suspend fun invoke(param: Int): Result<Product> = productRepository.getProduct(param)
}
