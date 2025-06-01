package com.roque.domain.repository

import com.roque.domain.model.Product
import com.roque.domain.util.Result

interface ProductRepository {

    suspend fun getProducts(): Result<List<Product>>

    suspend fun searchProducts(query: String, category: String?): Result<List<Product>>

    //suspend fun filterProducts(category: String): Result<List<Product>>

}