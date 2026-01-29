package com.tlpcraft.kmp.demo.feature.products.presentation.state

data class PaginationState<T>(
    val items: List<T> = emptyList(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    val hasMorePages: Boolean = true,
    val error: String? = null
) {
    fun loading(): PaginationState<T> = copy(isLoading = true, error = null)

    fun success(newItems: List<T>, pageSize: Int): PaginationState<T> = copy(
        items = items + newItems,
        currentPage = currentPage + 1,
        isLoading = false,
        hasMorePages = newItems.size == pageSize,
        error = null
    )

    fun error(message: String): PaginationState<T> = copy(
        isLoading = false,
        error = message
    )

    fun reset(): PaginationState<T> = PaginationState()

    val canLoadMore: Boolean
        get() = !isLoading && hasMorePages
}
