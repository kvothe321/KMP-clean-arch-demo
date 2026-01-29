package com.tlpcraft.kmp.demo.feature.products.presentation.helper

import com.tlpcraft.kmp.demo.application.usecase.GetProductsParams
import com.tlpcraft.kmp.demo.application.usecase.GetProductsUseCase
import com.tlpcraft.kmp.demo.domain.model.ProductPreview
import com.tlpcraft.kmp.demo.feature.products.presentation.state.PaginationManager
import com.tlpcraft.kmp.demo.feature.products.presentation.state.PaginationState

class ProductsPaginationHelper(
    private val getProductsUseCase: GetProductsUseCase,
    private val pageSize: Int = DEFAULT_PAGE_SIZE
) : PaginationManager<ProductPreview> {

    override suspend fun loadPage(state: PaginationState<ProductPreview>): PaginationState<ProductPreview> {
        if (!state.canLoadMore) return state

        val params = GetProductsParams(pageSize, state.currentPage * pageSize)
        return getProductsUseCase(params).fold(
            onSuccess = { products -> state.success(products, pageSize) },
            onFailure = { error -> state.error(error.message ?: "Unknown error") }
        )
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 4
    }
}
