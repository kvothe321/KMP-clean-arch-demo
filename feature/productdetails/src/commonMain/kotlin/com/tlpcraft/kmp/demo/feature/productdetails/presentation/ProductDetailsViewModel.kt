package com.tlpcraft.kmp.demo.feature.productdetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlpcraft.kmp.demo.application.usecase.AddFavoriteProductUseCase
import com.tlpcraft.kmp.demo.application.usecase.GetFavoriteProductsUseCase
import com.tlpcraft.kmp.demo.application.usecase.GetProductDetailsUseCase
import com.tlpcraft.kmp.demo.application.usecase.RemoveFavoriteProductUseCase
import com.tlpcraft.kmp.demo.feature.productdetails.presentation.state.ProductDetailsUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailsViewModel(
    initialProductId: Int,
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val getFavoriteProductsUseCase: GetFavoriteProductsUseCase,
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    private val removeFavoriteProductUseCase: RemoveFavoriteProductUseCase
) : ViewModel() {

    private val _productId = MutableStateFlow(initialProductId)
    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())

    private val _product = _productId.flatMapLatest { id ->
        flowOf(getProductDetailsUseCase(id))
    }

    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        observeFavorites()

        viewModelScope.launch {
            combine(_product, _favoriteIds) { productResult, favoriteIds ->
                productResult.fold(
                    onSuccess = { product ->
                        ProductDetailsUiState.Success(product, product.id in favoriteIds)
                    },
                    onFailure = {
                        ProductDetailsUiState.Error(it.message ?: "An unknown error occurred")
                    }
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun setProductId(id: Int) {
        _productId.value = id
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteProductsUseCase(Unit).collect { result ->
                result.onSuccess { products ->
                    _favoriteIds.value = products.map { it.id }.toSet()
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val currentProductId = _productId.value
            val isCurrentlyFavorite = _favoriteIds.value.contains(currentProductId)
            if (isCurrentlyFavorite) {
                removeFavoriteProductUseCase(currentProductId)
            } else {
                addFavoriteProductUseCase(currentProductId)
            }
        }
    }
}
