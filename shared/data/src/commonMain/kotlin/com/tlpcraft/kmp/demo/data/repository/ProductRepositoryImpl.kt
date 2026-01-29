package com.tlpcraft.kmp.demo.data.repository

import com.tlpcraft.kmp.demo.data.datasource.remote.ProductRemoteDataSource
import com.tlpcraft.kmp.demo.data.mapper.toProductPreview
import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository
import com.tlpcraft.kmp.demo.domain.service.DispatcherProvider
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val productRemoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun getProducts(limit: Int): Result<List<ProductPreview>> = runCatching {
        // TODO: add limit input validation
        withContext(dispatcherProvider.io) {
            productRemoteDataSource.getProducts(limit).map { it.toProductPreview() }
        }
    }

    override suspend fun getProduct(id: Int): Result<ProductPreview> = runCatching {
        withContext(dispatcherProvider.io) {
            productRemoteDataSource.getProduct(id).toProductPreview()
        }
    }
}
