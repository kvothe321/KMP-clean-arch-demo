package com.tlpcraft.kmp.demo.feature.products.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tlpcraft.kmp.demo.feature.products.presentation.components.EmptyContent
import com.tlpcraft.kmp.demo.feature.products.presentation.components.ErrorContent
import com.tlpcraft.kmp.demo.feature.products.presentation.components.LoadingContent
import com.tlpcraft.kmp.demo.feature.products.presentation.components.ProductsList
import com.tlpcraft.kmp.demo.feature.products.presentation.components.SearchBar
import com.tlpcraft.kmp.demo.feature.products.presentation.state.ProductsUiEvent
import com.tlpcraft.kmp.demo.feature.products.presentation.state.ProductsUiState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductsScreen(onProductClick: (Int) -> Unit = {}) {
    val viewModel = koinViewModel<ProductsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = when (val state = uiState) {
                is ProductsUiState.Loading -> state.searchQuery
                is ProductsUiState.Content -> state.searchQuery
                is ProductsUiState.Empty -> state.searchQuery
                is ProductsUiState.Error -> state.searchQuery
            },
            isSearchActive = when (val state = uiState) {
                is ProductsUiState.Loading -> state.isSearchActive
                is ProductsUiState.Content -> state.isSearchActive
                is ProductsUiState.Empty -> state.isSearchActive
                is ProductsUiState.Error -> state.isSearchActive
            },
            onQueryChange = { query ->
                viewModel.handleEvent(ProductsUiEvent.SearchQueryChanged(query))
            },
            onClearSearch = {
                viewModel.handleEvent(ProductsUiEvent.ClearSearch)
            }
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is ProductsUiState.Loading -> {
                    LoadingContent()
                }

                is ProductsUiState.Content -> {
                    ProductsList(
                        products = state.products,
                        hasMorePages = state.hasMorePages,
                        isLoadingMore = state.isLoadingMore,
                        onProductClick = onProductClick,
                        onLoadMore = { viewModel.handleEvent(ProductsUiEvent.LoadMore) },
                        onRefresh = { viewModel.handleEvent(ProductsUiEvent.Refresh) }
                    )
                }

                is ProductsUiState.Empty -> {
                    EmptyContent(
                        isSearchActive = state.isSearchActive,
                        searchQuery = state.searchQuery,
                        onClearSearch = { viewModel.handleEvent(ProductsUiEvent.ClearSearch) }
                    )
                }

                is ProductsUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.handleEvent(ProductsUiEvent.RetryAfterError) }
                    )
                }
            }
        }
    }
}
