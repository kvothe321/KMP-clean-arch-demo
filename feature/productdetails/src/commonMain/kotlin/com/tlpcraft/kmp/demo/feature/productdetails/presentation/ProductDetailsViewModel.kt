package com.tlpcraft.kmp.demo.feature.productdetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlpcraft.kmp.demo.application.usecase.AddFavoriteProductUseCase
import com.tlpcraft.kmp.demo.application.usecase.GetFavoriteProductsUseCase
import com.tlpcraft.kmp.demo.application.usecase.GetProductDetailsUseCase
import com.tlpcraft.kmp.demo.application.usecase.RemoveFavoriteProductUseCase
import com.tlpcraft.kmp.demo.feature.productdetails.presentation.state.ScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    private val removeFavoriteProductUseCase: RemoveFavoriteProductUseCase,
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ScreenUiState> = MutableStateFlow(ScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var currentProductId: Int = 0
    private val favoriteIds = mutableSetOf<Int>()

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteProductsUseCase().collect { result ->
                result.onSuccess { products ->
                    favoriteIds.clear()
                    favoriteIds.addAll(products.map { it.id })
                    val currentState = _uiState.value
                    if (currentState is ScreenUiState.Success) {
                        _uiState.value = currentState.copy(isFavorite = currentProductId in favoriteIds)
                    }
                }
            }
        }
    }

    fun loadProduct(id: Int) {
        currentProductId = id
        _uiState.value = ScreenUiState.Loading
        viewModelScope.launch {
            getProductDetailsUseCase(id)
                .onSuccess { product ->
                    _uiState.value = ScreenUiState.Success(
                        product = product,
                        isFavorite = id in favoriteIds
                    )
                }
                .onFailure { error ->
                    _uiState.value = ScreenUiState.Error(error.message ?: "Unknown error")
                }
        }
    }

    fun toggleFavorite() {
        val currentState = _uiState.value
        if (currentState is ScreenUiState.Success) {
            viewModelScope.launch {
                runCatching {
                    if (currentState.isFavorite) {
                        removeFavoriteProductUseCase(currentProductId)
                    } else {
                        addFavoriteProductUseCase(currentProductId)
                    }
                }
            }
        }
    }
}
