package com.roque.domain.usecase.product

import com.roque.domain.model.Product
import com.roque.domain.repository.ProductRepository
import com.roque.domain.util.Result

class GetProductsUseCase(private val repository: ProductRepository) {

    suspend operator fun invoke(): Result<List<Product>> = repository.getProducts()
}