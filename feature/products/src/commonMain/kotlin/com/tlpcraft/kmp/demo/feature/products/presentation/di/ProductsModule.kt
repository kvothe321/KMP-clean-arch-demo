package com.tlpcraft.kmp.demo.feature.products.presentation.di

import com.tlpcraft.kmp.demo.feature.products.presentation.ProductsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val productsModule = module {
    viewModelOf(::ProductsViewModel)
}
