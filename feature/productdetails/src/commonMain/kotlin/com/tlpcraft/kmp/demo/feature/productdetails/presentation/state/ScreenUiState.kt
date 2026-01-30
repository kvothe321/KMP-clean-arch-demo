package com.tlpcraft.kmp.demo.feature.productdetails.presentation.state

import com.tlpcraft.kmp.demo.domain.model.Product

sealed interface ProductDetailsUiState {
    data object Loading : ProductDetailsUiState

    data class Error(val message: String) : ProductDetailsUiState

    data class Success(val product: Product, val isFavorite: Boolean) : ProductDetailsUiState
}
