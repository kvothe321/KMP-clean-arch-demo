package com.tlpcraft.kmp.demo.di

import com.tlpcraft.kmp.demo.data.di.dataModule
import com.tlpcraft.kmp.demo.data.di.dataPlatformModule
import com.tlpcraft.kmp.demo.feature.products.presentation.di.productsModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(coreModule, dataPlatformModule, dataModule, productsModule)
}
