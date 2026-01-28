package com.tlpcraft.kmp.demo.data.networking.api

import com.tlpcraft.kmp.demo.data.model.Product
import com.tlpcraft.kmp.demo.data.model.Products
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ProductApi(private val httpClient: HttpClient) {

    suspend fun getProducts(limit: Int, skip: Int): List<Product> = httpClient.get("products") {
        parameter("limit", limit)
        parameter("skip", skip)
    }.body<Products>().products
}
