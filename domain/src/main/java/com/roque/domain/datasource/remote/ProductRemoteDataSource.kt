package com.roque.domain.datasource.remote

import com.roque.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRemoteDataSource {

    fun getAllProducts(): Flow<List<Product>>

    fun getProductById(id: String): Flow<Product?>

    fun searchProducts(query: String, category: String?): Flow<List<Product>>

}