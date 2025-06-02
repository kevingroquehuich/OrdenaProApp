package com.roque.domain.usecase.product

import com.roque.domain.model.Product
import com.roque.domain.repository.ProductRepository
import com.roque.domain.util.Result
import kotlinx.coroutines.flow.Flow

class SearchProductsUseCase(
    private val repository: ProductRepository
) {
    operator fun invoke(
        query: String,
        category: String? = null
    ): Flow<List<Product>> {
        return repository.searchProducts(query, category)
    }
}