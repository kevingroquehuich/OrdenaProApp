package com.roque.domain.usecase.product

import com.roque.domain.model.Product
import com.roque.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val repository: ProductRepository) {

    operator fun invoke(): Flow<List<Product>> = repository.getAllProducts()
}