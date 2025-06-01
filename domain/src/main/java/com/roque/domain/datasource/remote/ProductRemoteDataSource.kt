package com.roque.domain.datasource.remote

import com.roque.domain.model.Product
import com.roque.domain.util.Result

interface ProductRemoteDataSource {
    suspend fun fetchProducts(): Result<List<Product>>

    suspend fun searchProducts(query: String, category: String?): Result<List<Product>>
}