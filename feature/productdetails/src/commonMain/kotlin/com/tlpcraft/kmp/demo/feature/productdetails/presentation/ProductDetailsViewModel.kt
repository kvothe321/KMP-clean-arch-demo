package com.tlpcraft.kmp.demo.feature.productdetails.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlpcraft.kmp.demo.application.usecase.GetProductDetailsUseCase
import com.tlpcraft.kmp.demo.feature.productdetails.presentation.state.ScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val productId: Int,
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ScreenUiState> = MutableStateFlow(ScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getProductDetails()
    }

    private fun getProductDetails() {
        viewModelScope.launch {
            getProductDetailsUseCase(productId)
                .onSuccess { product ->
                    _uiState.value = ScreenUiState.Success(product)
                }
                .onFailure { error ->
                    _uiState.value = ScreenUiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}
