package com.tlpcraft.kmp.demo.feature.products.presentation.state

import com.tlpcraft.kmp.demo.domain.model.ProductPreview

sealed interface ProductsUiState {
    data class Loading(
        val searchQuery: String = "",
        val isSearchActive: Boolean = false
    ) : ProductsUiState

    data class Content(
        val products: List<ProductPreview>,
        val isLoadingMore: Boolean = false,
        val hasMorePages: Boolean = true,
        val searchQuery: String = "",
        val isSearchActive: Boolean = false
    ) : ProductsUiState

    data class Empty(
        val isSearchActive: Boolean = false,
        val searchQuery: String = ""
    ) : ProductsUiState

    data class Error(
        val message: String,
        val isSearchActive: Boolean = false,
        val searchQuery: String = ""
    ) : ProductsUiState
}
