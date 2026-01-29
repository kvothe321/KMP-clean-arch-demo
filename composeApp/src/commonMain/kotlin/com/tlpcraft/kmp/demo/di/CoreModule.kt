package com.tlpcraft.kmp.demo.di

import com.tlpcraft.kmp.demo.application.serviceimpl.DispatcherProviderImpl
import com.tlpcraft.kmp.demo.application.usecase.AddFavoriteProductUseCase
import com.tlpcraft.kmp.demo.application.usecase.GetFavoriteProductsUseCase
import com.tlpcraft.kmp.demo.application.usecase.GetProductDetailsUseCase
import com.tlpcraft.kmp.demo.application.usecase.GetProductsUseCase
import com.tlpcraft.kmp.demo.application.usecase.RemoveFavoriteProductUseCase
import com.tlpcraft.kmp.demo.domain.service.DispatcherProvider
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    singleOf(::DispatcherProviderImpl) bind DispatcherProvider::class

    factoryOf(::GetProductsUseCase)
    factoryOf(::GetProductDetailsUseCase)
    factoryOf(::GetFavoriteProductsUseCase)
    factoryOf(::AddFavoriteProductUseCase)
    factoryOf(::RemoveFavoriteProductUseCase)
}
