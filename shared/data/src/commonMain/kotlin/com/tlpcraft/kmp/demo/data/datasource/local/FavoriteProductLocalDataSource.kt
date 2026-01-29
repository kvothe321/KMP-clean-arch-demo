package com.tlpcraft.kmp.demo.data.datasource.local

interface FavoriteProductLocalDataSource {
    suspend fun getFavoriteProductIds(): List<Int>

    suspend fun addFavoriteProduct(id: Int)

    suspend fun removeFavoriteProduct(id: Int)
}
