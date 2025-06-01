package com.roque.domain.usecase.product

import com.roque.domain.model.Category
import com.roque.domain.repository.ProductRepository
import com.roque.domain.util.Result

class GetCategoriesUseCase(private val repository: ProductRepository) {

    suspend operator fun invoke(): Result<List<Category>> = repository.getCategories()

}