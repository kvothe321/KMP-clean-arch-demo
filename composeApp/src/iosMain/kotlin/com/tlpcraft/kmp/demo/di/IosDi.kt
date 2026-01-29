package com.tlpcraft.kmp.demo.di

import com.tlpcraft.kmp.demo.MyViewModel
import com.tlpcraft.kmp.demo.application.usecase.MyUseCase
import com.tlpcraft.kmp.demo.data.di.dataModule
import com.tlpcraft.kmp.demo.data.di.dataPlatformModule
import kotlin.experimental.ExperimentalObjCName
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val myDiModule = module {
    factoryOf(::MyUseCase)
    viewModelOf(::MyViewModel)
}

fun initKoin() = startKoin {
    modules(myDiModule, dataPlatformModule, dataModule)
}

@OptIn(ExperimentalObjCName::class)
@ObjCName("IosDependencies")
class IosDependencies : KoinComponent {
    val viewModel: MyViewModel by inject()
}
