package com.tlpcraft.kmp.demo.data.networking.api

import com.tlpcraft.kmp.demo.data.model.ProductDto
import com.tlpcraft.kmp.demo.data.model.Products
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProductApiImpl(private val httpClient: HttpClient) : ProductApi {

    override suspend fun getProducts(limit: Int, skip: Int): List<ProductDto> = httpClient.get("products") {
        parameter("limit", limit)
        parameter("skip", skip)
    }.body<Products>().products

    override suspend fun searchProducts(query: String, limit: Int, skip: Int): List<ProductDto> = httpClient.get("products/search") {
        parameter("q", query)
        parameter("limit", limit)
        parameter("skip", skip)
    }.body<Products>().products

    override suspend fun getProduct(id: Int): ProductDto = httpClient.get("products/$id").body()
}
