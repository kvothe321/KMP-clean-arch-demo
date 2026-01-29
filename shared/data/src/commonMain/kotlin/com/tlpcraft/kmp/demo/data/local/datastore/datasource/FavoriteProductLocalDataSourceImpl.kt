package com.tlpcraft.kmp.demo.data.local.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tlpcraft.kmp.demo.data.datasource.local.FavoriteProductLocalDataSource
import com.tlpcraft.kmp.demo.domain.service.DispatcherProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FavoriteProductLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
    private val dispatcherProvider: DispatcherProvider
) : FavoriteProductLocalDataSource {

    override suspend fun getFavoriteProductIds(): List<Int> = withContext(dispatcherProvider.io) {
        dataStore.data.map { preferences ->
            preferences[FAVORITE_PRODUCT_IDS_KEY]
                ?.split(",")
                ?.filter { it.isNotBlank() }
                ?.mapNotNull { it.toIntOrNull() }
                ?: emptyList()
        }.first()
    }

    override suspend fun addFavoriteProduct(id: Int) {
        withContext(dispatcherProvider.io) {
            dataStore.edit { preferences ->
                val currentIds = preferences[FAVORITE_PRODUCT_IDS_KEY]?.split(",")?.filter { it.isNotBlank() }?.toMutableSet() ?: mutableSetOf()
                currentIds.add(id.toString())
                preferences[FAVORITE_PRODUCT_IDS_KEY] = currentIds.joinToString(",")
            }
        }
    }

    override suspend fun removeFavoriteProduct(id: Int) {
        withContext(dispatcherProvider.io) {
            dataStore.edit { preferences ->
                val currentIds = preferences[FAVORITE_PRODUCT_IDS_KEY]?.split(",")?.filter { it.isNotBlank() }?.toMutableSet() ?: mutableSetOf()
                currentIds.remove(id.toString())
                preferences[FAVORITE_PRODUCT_IDS_KEY] = currentIds.joinToString(",")
            }
        }
    }

    companion object {
        private val FAVORITE_PRODUCT_IDS_KEY = stringPreferencesKey("favorite_product_ids")
    }
}
