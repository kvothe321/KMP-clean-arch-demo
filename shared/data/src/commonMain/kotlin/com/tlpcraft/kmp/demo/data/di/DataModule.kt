package com.tlpcraft.kmp.demo.data.di

import com.tlpcraft.kmp.demo.data.datasource.local.FavoriteProductLocalDataSource
import com.tlpcraft.kmp.demo.data.datasource.remote.ProductRemoteDataSource
import com.tlpcraft.kmp.demo.data.local.datastore.datasource.FavoriteProductLocalDataSourceImpl
import com.tlpcraft.kmp.demo.data.networking.api.ProductApi
import com.tlpcraft.kmp.demo.data.networking.api.ProductApiImpl
import com.tlpcraft.kmp.demo.data.networking.client.HttpClientFactory
import com.tlpcraft.kmp.demo.data.networking.datasource.ProductRemoteDataSourceImpl
import com.tlpcraft.kmp.demo.data.repository.FavoriteProductRepositoryImpl
import com.tlpcraft.kmp.demo.data.repository.ProductRepositoryImpl
import com.tlpcraft.kmp.demo.domain.repository.FavoriteProductRepository
import com.tlpcraft.kmp.demo.domain.repository.ProductRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { HttpClientFactory(get()).create() }

    singleOf(::ProductApiImpl) bind ProductApi::class
    singleOf(::ProductRepositoryImpl) bind ProductRepository::class
    singleOf(::ProductRemoteDataSourceImpl) bind ProductRemoteDataSource::class

    singleOf(::FavoriteProductLocalDataSourceImpl) bind FavoriteProductLocalDataSource::class
    singleOf(::FavoriteProductRepositoryImpl) bind FavoriteProductRepository::class
}
