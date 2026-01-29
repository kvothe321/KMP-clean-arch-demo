package com.tlpcraft.kmp.demo.feature.productdetails.presentation.state

import com.tlpcraft.kmp.demo.domain.model.ProductPreview

sealed interface ScreenUiState {
    data object Loading : ScreenUiState

    data class Success(val product: ProductPreview, val isFavorite: Boolean) : ScreenUiState

    data class Error(val message: String) : ScreenUiState
}
