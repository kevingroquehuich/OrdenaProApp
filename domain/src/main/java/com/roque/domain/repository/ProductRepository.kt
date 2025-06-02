package com.roque.domain.repository

import com.roque.domain.model.Category
import com.roque.domain.model.Product
import com.roque.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getAllProducts(): Flow<List<Product>>

    fun getProductById(id: String): Flow<Product?>

    fun searchProducts(query: String, category: String?): Flow<List<Product>>

    fun getCategories(): Flow<List<Category>>

}