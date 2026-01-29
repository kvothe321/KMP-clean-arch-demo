package com.tlpcraft.kmp.demo.di

import com.tlpcraft.kmp.demo.data.di.dataModule
import com.tlpcraft.kmp.demo.data.di.dataPlatformModule
import com.tlpcraft.kmp.demo.feature.products.presentation.ProductsViewModel
import com.tlpcraft.kmp.demo.feature.products.presentation.di.productsModule
import kotlin.experimental.ExperimentalObjCName
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(coreModule, dataPlatformModule, dataModule, productsModule)
}

@OptIn(ExperimentalObjCName::class)
@ObjCName("IosDependencies")
class IosDependencies : KoinComponent {
    val viewModel: ProductsViewModel by inject()
}
