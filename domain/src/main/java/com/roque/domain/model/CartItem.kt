package com.roque.domain.model

data class CartItem(
    val id: Int = 0,
    val productId: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val quantity: Int
)

