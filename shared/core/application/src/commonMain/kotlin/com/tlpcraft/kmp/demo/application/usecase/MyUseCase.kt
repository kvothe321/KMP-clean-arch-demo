package com.tlpcraft.kmp.demo.application.usecase

import com.tlpcraft.kmp.demo.domain.model.MyData
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository
import com.tlpcraft.kmp.demo.domain.usecase.UseCase

class MyUseCase(private val productRepository: ProductRepository) : UseCase<Unit, MyData> {
    override suspend fun invoke(param: Unit) = productRepository.getProducts()
}
