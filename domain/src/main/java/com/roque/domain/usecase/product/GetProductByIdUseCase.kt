package com.roque.domain.usecase.product

import com.roque.domain.repository.ProductRepository

class GetProductByIdUseCase(private val repository: ProductRepository) {

    operator fun invoke(productId: String) = repository.getProductById(productId)

}