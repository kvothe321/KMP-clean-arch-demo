package com.tlpcraft.kmp.demo.feature.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tlpcraft.kmp.demo.application.usecase.GetProductsParams
import com.tlpcraft.kmp.demo.application.usecase.GetProductsUseCase
import com.tlpcraft.kmp.demo.application.usecase.SearchProductsParams
import com.tlpcraft.kmp.demo.application.usecase.SearchProductsUseCase
import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.feature.products.presentation.state.ProductsUiEvent
import com.tlpcraft.kmp.demo.feature.products.presentation.state.ProductsUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Disclaimer: I know this class is doing too much, but for the sake of the demo I am leaving it as it is
class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductsUiState>(
        ProductsUiState.Loading(searchQuery = "", isSearchActive = false)
    )
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null
    private var currentProducts: List<ProductPreview> = emptyList()
    private var currentPage: Int = 0
    private var hasMorePages: Boolean = true
    private var currentSearchQuery: String = ""

    init {
        handleEvent(ProductsUiEvent.LoadInitial)
    }

    fun handleEvent(event: ProductsUiEvent) {
        when (event) {
            ProductsUiEvent.LoadInitial -> loadInitialProducts()
            ProductsUiEvent.LoadMore -> loadMoreProducts()
            ProductsUiEvent.Refresh -> refreshProducts()
            is ProductsUiEvent.SearchQueryChanged -> handleSearchQueryChange(event.query)
            ProductsUiEvent.ClearSearch -> clearSearch()
            ProductsUiEvent.RetryAfterError -> retryAfterError()
        }
    }

    private fun loadInitialProducts() {
        loadJob?.cancel()
        _uiState.value = ProductsUiState.Loading(searchQuery = "", isSearchActive = false)

        loadJob = viewModelScope.launch {
            resetPagination()

            val params = GetProductsParams(PAGE_SIZE, 0)
            getProductsUseCase(params).fold(
                onSuccess = { products ->
                    currentProducts = products
                    currentPage = 1
                    hasMorePages = products.size == PAGE_SIZE

                    _uiState.value = if (products.isEmpty()) {
                        ProductsUiState.Empty(isSearchActive = false, searchQuery = "")
                    } else {
                        ProductsUiState.Content(
                            products = products,
                            hasMorePages = hasMorePages,
                            searchQuery = "",
                            isSearchActive = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.value = ProductsUiState.Error(
                        message = error.message ?: "Failed to load products",
                        isSearchActive = false,
                        searchQuery = ""
                    )
                }
            )
        }
    }

    private fun loadMoreProducts() {
        val currentState = _uiState.value
        if (currentState !is ProductsUiState.Content || !hasMorePages || loadJob?.isActive == true) {
            return
        }

        _uiState.value = currentState.copy(isLoadingMore = true)

        loadJob = viewModelScope.launch {
            if (currentState.isSearchActive) {
                loadMoreSearchResults(currentState)
            } else {
                loadMoreRegularProducts(currentState)
            }
        }
    }

    private suspend fun loadMoreRegularProducts(currentState: ProductsUiState.Content) {
        val params = GetProductsParams(PAGE_SIZE, currentPage * PAGE_SIZE)
        getProductsUseCase(params).fold(
            onSuccess = { products ->
                currentProducts = currentProducts + products
                currentPage++
                hasMorePages = products.size == PAGE_SIZE

                _uiState.value = currentState.copy(
                    products = currentProducts,
                    isLoadingMore = false,
                    hasMorePages = hasMorePages
                )
            },
            onFailure = { error ->
                _uiState.value = ProductsUiState.Error(
                    message = error.message ?: "Failed to load more products",
                    isSearchActive = false,
                    searchQuery = ""
                )
            }
        )
    }

    private suspend fun loadMoreSearchResults(currentState: ProductsUiState.Content) {
        val params = SearchProductsParams(currentSearchQuery, PAGE_SIZE, currentPage * PAGE_SIZE)
        searchProductsUseCase(params).fold(
            onSuccess = { products ->
                currentProducts = currentProducts + products
                currentPage++
                hasMorePages = products.size == PAGE_SIZE

                _uiState.value = currentState.copy(
                    products = currentProducts,
                    isLoadingMore = false,
                    hasMorePages = hasMorePages
                )
            },
            onFailure = { error ->
                _uiState.value = ProductsUiState.Error(
                    message = error.message ?: "Failed to load more search results",
                    isSearchActive = true,
                    searchQuery = currentSearchQuery
                )
            }
        )
    }

    private fun handleSearchQueryChange(query: String) {
        loadJob?.cancel()
        currentSearchQuery = query.trim()

        if (currentSearchQuery.isEmpty()) {
            clearSearch()
            return
        }

        _uiState.value = ProductsUiState.Loading(
            searchQuery = currentSearchQuery,
            isSearchActive = true
        )

        loadJob = viewModelScope.launch {
            resetPagination()

            val params = SearchProductsParams(currentSearchQuery, PAGE_SIZE, 0)
            searchProductsUseCase(params).fold(
                onSuccess = { products ->
                    currentProducts = products
                    currentPage = 1
                    hasMorePages = products.size == PAGE_SIZE

                    _uiState.value = if (products.isEmpty()) {
                        ProductsUiState.Empty(
                            isSearchActive = true,
                            searchQuery = currentSearchQuery
                        )
                    } else {
                        ProductsUiState.Content(
                            products = products,
                            hasMorePages = hasMorePages,
                            searchQuery = currentSearchQuery,
                            isSearchActive = true
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.value = ProductsUiState.Error(
                        message = error.message ?: "Search failed",
                        isSearchActive = true,
                        searchQuery = currentSearchQuery
                    )
                }
            )
        }
    }

    private fun clearSearch() {
        loadJob?.cancel()
        currentSearchQuery = ""
        loadInitialProducts()
    }

    private fun refreshProducts() {
        loadJob?.cancel()
        val isSearchActive = when (val currentState = _uiState.value) {
            is ProductsUiState.Content -> currentState.isSearchActive
            is ProductsUiState.Loading -> currentState.isSearchActive
            is ProductsUiState.Empty -> currentState.isSearchActive
            is ProductsUiState.Error -> currentState.isSearchActive
        }

        if (isSearchActive && currentSearchQuery.isNotEmpty()) {
            handleSearchQueryChange(currentSearchQuery)
        } else {
            loadInitialProducts()
        }
    }

    private fun retryAfterError() {
        val errorState = _uiState.value as? ProductsUiState.Error
        if (errorState?.isSearchActive == true && currentSearchQuery.isNotEmpty()) {
            handleSearchQueryChange(currentSearchQuery)
        } else {
            loadInitialProducts()
        }
    }

    private fun resetPagination() {
        currentProducts = emptyList()
        currentPage = 0
        hasMorePages = true
    }

    companion object {
        private const val PAGE_SIZE = 4
    }
}
