package com.tlpcraft.kmp.demo.feature.productdetails.presentation.di

import com.tlpcraft.kmp.demo.feature.productdetails.presentation.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val productDetailsModule = module {
    viewModel { (productId: Int) -> ProductDetailsViewModel(productId, get()) }
}
