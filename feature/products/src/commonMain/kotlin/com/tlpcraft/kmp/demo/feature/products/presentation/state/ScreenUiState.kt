package com.tlpcraft.kmp.demo.feature.products.presentation.state

import com.tlpcraft.kmp.demo.domain.model.ProductPreview

sealed interface ScreenUiState {
    data object Loading : ScreenUiState

    data class Success(val products: List<ProductPreview>) : ScreenUiState

    data class Error(val message: String) : ScreenUiState
}
