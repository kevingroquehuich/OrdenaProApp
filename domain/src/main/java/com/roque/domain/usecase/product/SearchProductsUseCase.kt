package com.roque.domain.usecase.product

import com.roque.domain.model.Product
import com.roque.domain.repository.ProductRepository
import com.roque.domain.util.Result

class SearchProductsUseCase(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        query: String,
        category: String? = null
    ): Result<List<Product>> {
        return repository.searchProducts(query, category)
    }
}