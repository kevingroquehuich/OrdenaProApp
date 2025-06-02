package com.roque.ordenaproapp.ui.screens.products.list

import com.roque.domain.model.Category
import com.roque.domain.model.Product

data class ProductUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList()
)