package com.tlpcraft.kmp.demo.domain.repository

import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import kotlinx.coroutines.flow.Flow

interface FavoriteProductRepository {

    fun getFavoriteProducts(): Flow<Result<List<ProductPreview>>>

    suspend fun addFavoriteProduct(id: Int)

    suspend fun removeFavoriteProduct(id: Int)
}
