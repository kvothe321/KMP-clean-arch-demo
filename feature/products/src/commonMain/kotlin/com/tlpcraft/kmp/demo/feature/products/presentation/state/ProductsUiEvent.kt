package com.tlpcraft.kmp.demo.feature.products.presentation.state

sealed interface ProductsUiEvent {
    data object LoadInitial : ProductsUiEvent

    data object LoadMore : ProductsUiEvent

    data object Refresh : ProductsUiEvent

    data class SearchQueryChanged(val query: String) : ProductsUiEvent

    data object ClearSearch : ProductsUiEvent

    data object RetryAfterError : ProductsUiEvent
}
