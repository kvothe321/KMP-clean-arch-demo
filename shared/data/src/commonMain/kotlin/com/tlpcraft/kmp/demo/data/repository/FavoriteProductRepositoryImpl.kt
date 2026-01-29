package com.tlpcraft.kmp.demo.data.repository

import com.tlpcraft.kmp.demo.data.datasource.local.FavoriteProductLocalDataSource
import com.tlpcraft.kmp.demo.data.datasource.remote.ProductRemoteDataSource
import com.tlpcraft.kmp.demo.data.mapper.toProductPreview
import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.domain.repository.FavoriteProductRepository
import com.tlpcraft.kmp.demo.domain.service.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class FavoriteProductRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val favoriteProductLocalDataSource: FavoriteProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource
) : FavoriteProductRepository {

    override fun getFavoriteProducts(): Flow<Result<List<ProductPreview>>> = flow {
        emit(
            runCatching {
                withContext(dispatcherProvider.io) {
                    val favoriteIds = favoriteProductLocalDataSource.getFavoriteProductIds()
                    favoriteIds.mapNotNull { id ->
                        runCatching {
                            productRemoteDataSource.getProduct(id).toProductPreview()
                        }.getOrNull()
                    }
                }
            }
        )
    }

    override suspend fun addFavoriteProduct(id: Int) {
        withContext(dispatcherProvider.io) {
            favoriteProductLocalDataSource.addFavoriteProduct(id)
        }
    }

    override suspend fun removeFavoriteProduct(id: Int) {
        withContext(dispatcherProvider.io) {
            favoriteProductLocalDataSource.removeFavoriteProduct(id)
        }
    }
}
