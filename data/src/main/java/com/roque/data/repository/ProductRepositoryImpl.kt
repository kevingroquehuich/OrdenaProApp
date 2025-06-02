package com.roque.data.repository

import com.roque.domain.datasource.remote.CategoryRemoteDataSource
import com.roque.domain.datasource.remote.ProductRemoteDataSource
import com.roque.domain.model.Category
import com.roque.domain.model.Product
import com.roque.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val categoryRemoteDataSource: CategoryRemoteDataSource
) : ProductRepository {

    override fun getAllProducts(): Flow<List<Product>> =
        productRemoteDataSource.getAllProducts()

    override fun getProductById(id: String): Flow<Product?> =
        productRemoteDataSource.getProductById(id)

    override fun searchProducts(query: String, category: String?): Flow<List<Product>> =
        productRemoteDataSource.searchProducts(query, category)

    override fun getCategories(): Flow<List<Category>> =
        categoryRemoteDataSource.getCategories()
}