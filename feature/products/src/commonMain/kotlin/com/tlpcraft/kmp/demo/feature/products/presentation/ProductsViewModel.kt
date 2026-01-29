package com.tlpcraft.kmp.demo.feature.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlpcraft.kmp.demo.application.usecase.GetProductsUseCase
import com.tlpcraft.kmp.demo.application.usecase.SearchProductsUseCase
import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.feature.products.presentation.helper.ProductsPaginationHelper
import com.tlpcraft.kmp.demo.feature.products.presentation.helper.SearchPaginationHelper
import com.tlpcraft.kmp.demo.feature.products.presentation.state.PaginationState
import com.tlpcraft.kmp.demo.feature.products.presentation.state.ScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// TODO: UNIIFY STATE MANAGEMENT
class ProductsViewModel(
    getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val paginationHelper = ProductsPaginationHelper(getProductsUseCase)
    private val searchPaginationHelper = SearchPaginationHelper(searchProductsUseCase)

    private val _paginationState = MutableStateFlow(PaginationState<ProductPreview>())
    private val _uiState = MutableStateFlow<ScreenUiState>(ScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _isSearchMode = MutableStateFlow(false)
    val isSearchMode = _isSearchMode.asStateFlow()

    init {
        loadProducts()
    }

    // TODO: Search feels like a standalone feature on its own (or at least makes the view model a god class)
    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.isBlank()) {
            exitSearchMode()
        } else {
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        _isSearchMode.value = true
        _paginationState.value = PaginationState()
        _uiState.value = ScreenUiState.Loading

        viewModelScope.launch {
            val currentState = _paginationState.value
            _paginationState.value = currentState.loading()

            val newState = searchPaginationHelper.loadPage(currentState, query)
            _paginationState.value = newState
            updateUiState(newState)
        }
    }

    fun loadMore() {
        if (_isSearchMode.value) {
            searchMore()
        } else {
            loadProducts()
        }
    }

    private fun searchMore() {
        if (!_paginationState.value.canLoadMore) return

        viewModelScope.launch {
            val currentState = _paginationState.value
            _paginationState.value = currentState.loading()

            val newState = searchPaginationHelper.loadPage(currentState, _searchQuery.value)
            _paginationState.value = newState
            updateUiState(newState)
        }
    }

    fun exitSearchMode() {
        _isSearchMode.value = false
        _searchQuery.value = ""
        refresh()
    }

    fun loadProducts() {
        if (!_paginationState.value.canLoadMore || _isSearchMode.value) return

        viewModelScope.launch {
            val currentState = _paginationState.value
            _paginationState.value = currentState.loading()

            val newState = paginationHelper.loadPage(currentState)
            _paginationState.value = newState
            updateUiState(newState)
        }
    }

    fun refresh() {
        _paginationState.value = PaginationState()
        _uiState.value = ScreenUiState.Loading
        loadProducts()
    }

    private fun updateUiState(state: PaginationState<ProductPreview>) {
        _uiState.value = when {
            state.error != null -> ScreenUiState.Error(state.error)

            state.items.isEmpty() && state.isLoading -> ScreenUiState.Loading

            else -> ScreenUiState.Success(
                products = state.items,
                hasMore = state.hasMorePages
            )
        }
    }
}
