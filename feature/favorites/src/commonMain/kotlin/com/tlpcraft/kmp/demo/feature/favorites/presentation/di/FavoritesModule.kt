package com.tlpcraft.kmp.demo.feature.favorites.presentation.di

import com.tlpcraft.kmp.demo.feature.favorites.presentation.FavoritesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val favoritesModule = module {
    viewModelOf(::FavoritesViewModel)
}
