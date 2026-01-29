package com.tlpcraft.kmp.demo.feature.products.presentation.helper

import com.tlpcraft.kmp.demo.application.usecase.SearchProductsParams
import com.tlpcraft.kmp.demo.application.usecase.SearchProductsUseCase
import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.feature.products.presentation.state.PaginationState

class SearchPaginationHelper(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val pageSize: Int = ProductsPaginationHelper.DEFAULT_PAGE_SIZE
) {
    suspend fun loadPage(state: PaginationState<ProductPreview>, query: String): PaginationState<ProductPreview> {
        if (!state.canLoadMore || query.isBlank()) return state

        val params = SearchProductsParams(query, pageSize, state.currentPage * pageSize)
        return searchProductsUseCase(params).fold(
            onSuccess = { products -> state.success(products, pageSize) },
            onFailure = { error -> state.error(error.message ?: "Unknown error") }
        )
    }
}
