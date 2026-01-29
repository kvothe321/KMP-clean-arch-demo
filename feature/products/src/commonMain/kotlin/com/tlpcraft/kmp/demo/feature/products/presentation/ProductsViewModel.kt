package com.tlpcraft.kmp.demo.feature.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlpcraft.kmp.demo.application.usecase.GetProductsUseCase
import com.tlpcraft.kmp.demo.feature.products.presentation.state.ScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel(private val myUseCase: GetProductsUseCase) : ViewModel() {
    private val _uiState: MutableStateFlow<ScreenUiState> = MutableStateFlow(ScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            myUseCase(10)
                .onSuccess { products ->
                    _uiState.value = ScreenUiState.Success(products)
                }
                .onFailure { error ->
                    _uiState.value = ScreenUiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}
