package com.roque.data.datasource.remote

import com.roque.data.network.api.ProductApi
import com.roque.domain.datasource.remote.ProductRemoteDataSource
import com.roque.domain.model.Category
import com.roque.domain.model.Product
import com.roque.domain.util.Failure
import com.roque.domain.util.Result
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val api: ProductApi
) : ProductRemoteDataSource {

    override suspend fun fetchProducts(): Result<List<Product>> {
        return try {
            val response = api.getAllProducts()
            if (response.isSuccessful) {
                val products = response.body()?.data.orEmpty()
                Result.Success(products.map { it.toDomain() })
            } else {
                Result.Error(Failure.ServerError)
            }
        } catch (e: Exception) {
            Result.Error(Failure.NetworkError)
        }
    }

    override suspend fun searchProducts(query: String, category: String?): Result<List<Product>> {
        return try {
            val response = when {
                query.isNotBlank() -> api.searchProducts(query)
                else -> api.getAllProducts(limit = 100)
            }

            if (response.isSuccessful) {
                var products = response.body()?.data.orEmpty()

                if (!category.isNullOrBlank()) {
                    products = products.filter { it.category.equals(category, ignoreCase = true) }
                }

                Result.Success(products.map { it.toDomain() })
            } else {
                Result.Error(Failure.ServerError)
            }
        } catch (e: Exception) {
            Result.Error(Failure.NetworkError)
        }
    }

    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            val response = api.getCategories()
            if (response.isSuccessful) {
                val categories = response.body()?.map { it.toDomain() }?.sortedBy { it.name }.orEmpty()
                Result.Success(categories)
            } else {
                Result.Error(Failure.ServerError)
            }
        } catch (e: Exception) {
            Result.Error(Failure.NetworkError)
        }
    }
}