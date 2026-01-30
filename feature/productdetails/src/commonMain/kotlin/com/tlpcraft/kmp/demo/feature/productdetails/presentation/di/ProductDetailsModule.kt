package com.tlpcraft.kmp.demo.feature.productdetails.presentation.di

import com.tlpcraft.kmp.demo.feature.productdetails.presentation.ProductDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val productDetailsModule = module {
    viewModelOf(::ProductDetailsViewModel)
}
