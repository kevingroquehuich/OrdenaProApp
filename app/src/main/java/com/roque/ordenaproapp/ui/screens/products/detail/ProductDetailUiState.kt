package com.roque.ordenaproapp.ui.screens.products.detail

import com.roque.domain.model.Product

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false,
    val product: Product? = null
)