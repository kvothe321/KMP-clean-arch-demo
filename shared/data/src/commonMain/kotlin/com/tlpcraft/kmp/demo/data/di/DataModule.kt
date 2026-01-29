package com.tlpcraft.kmp.demo.data.di

import com.tlpcraft.kmp.demo.data.datasource.remote.ProductRemoteDataSource
import com.tlpcraft.kmp.demo.data.networking.api.ProductApi
import com.tlpcraft.kmp.demo.data.networking.client.HttpClientFactory
import com.tlpcraft.kmp.demo.data.networking.datasource.ProductRemoteDataSourceImpl
import com.tlpcraft.kmp.demo.data.repository.ProductRepositoryImpl
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { HttpClientFactory(get()).create() }
    singleOf(::ProductApi)
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class
    singleOf(::ProductRemoteDataSourceImpl) bind ProductRemoteDataSource::class
}
