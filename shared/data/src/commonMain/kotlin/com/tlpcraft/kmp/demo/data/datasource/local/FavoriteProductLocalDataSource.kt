package com.tlpcraft.kmp.demo.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface FavoriteProductLocalDataSource {
    fun getFavoriteProductIds(): Flow<List<Int>>

    suspend fun addFavoriteProduct(id: Int)

    suspend fun removeFavoriteProduct(id: Int)
}
