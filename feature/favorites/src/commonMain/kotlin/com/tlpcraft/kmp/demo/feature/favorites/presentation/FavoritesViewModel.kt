package com.tlpcraft.kmp.demo.feature.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlpcraft.kmp.demo.application.usecase.GetFavoriteProductsUseCase
import com.tlpcraft.kmp.demo.feature.favorites.presentation.state.ScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScreenUiState>(ScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getFavoriteProducts()
    }

    private fun getFavoriteProducts() {
        viewModelScope.launch {
            getFavoriteProductsUseCase()
                .collect { result ->
                    result
                        .onSuccess { products ->
                            _uiState.value = if (products.isEmpty()) {
                                ScreenUiState.Empty
                            } else {
                                ScreenUiState.Success(products)
                            }
                        }
                        .onFailure { error ->
                            _uiState.value = ScreenUiState.Error(error.message ?: "Unknown error")
                        }
                }
        }
    }
}
