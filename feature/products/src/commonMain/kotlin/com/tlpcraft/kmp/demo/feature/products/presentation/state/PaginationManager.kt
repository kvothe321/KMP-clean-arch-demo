package com.tlpcraft.kmp.demo.feature.products.presentation.state

interface PaginationManager<T> {
    suspend fun loadPage(state: PaginationState<T>): PaginationState<T>
}
