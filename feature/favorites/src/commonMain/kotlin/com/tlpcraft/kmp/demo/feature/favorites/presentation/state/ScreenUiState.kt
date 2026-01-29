package com.tlpcraft.kmp.demo.feature.favorites.presentation.state

import com.tlpcraft.kmp.demo.domain.model.ProductPreview

sealed interface ScreenUiState {
    data object Loading : ScreenUiState

    data object Empty : ScreenUiState

    data class Success(val products: List<ProductPreview>) : ScreenUiState

    data class Error(val message: String) : ScreenUiState
}
