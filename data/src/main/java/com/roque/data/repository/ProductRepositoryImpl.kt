package com.roque.data.repository

import com.roque.domain.datasource.remote.ProductRemoteDataSource
import com.roque.domain.model.Category
import com.roque.domain.model.Product
import com.roque.domain.repository.ProductRepository
import com.roque.domain.util.Result
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remote: ProductRemoteDataSource
): ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return when (val result = remote.fetchProducts()) {
            is Result.Success -> Result.Success(result.data)
            is Result.Error -> Result.Error(result.type)
        }
    }

    override suspend fun searchProducts(query: String, category: String?): Result<List<Product>> {
        return when (val result = remote.searchProducts(query, category)) {
            is Result.Success -> Result.Success(result.data)
            is Result.Error -> Result.Error(result.type)
        }
    }

    override suspend fun getCategories(): Result<List<Category>> {
        return when (val result = remote.getCategories()) {
            is Result.Success -> Result.Success(result.data)
            is Result.Error -> Result.Error(result.type)
        }
    }

}