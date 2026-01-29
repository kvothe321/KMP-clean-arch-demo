package com.tlpcraft.kmp.demo.di

import com.tlpcraft.kmp.demo.MyViewModel
import com.tlpcraft.kmp.demo.application.usecase.MyUseCase
import com.tlpcraft.kmp.demo.data.di.dataModule
import com.tlpcraft.kmp.demo.data.di.dataPlatformModule
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val myDiModule = module {
    factoryOf(::MyUseCase)

    viewModelOf(::MyViewModel)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(myDiModule, dataPlatformModule, dataModule)
}
