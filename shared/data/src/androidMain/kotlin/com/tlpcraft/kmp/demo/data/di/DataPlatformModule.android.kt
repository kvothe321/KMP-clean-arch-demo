package com.tlpcraft.kmp.demo.data.di

import com.tlpcraft.kmp.demo.data.local.datastore.createDataStore
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val dataPlatformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { createDataStore(androidContext()) }
    }
