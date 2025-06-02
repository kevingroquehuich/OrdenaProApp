package com.roque.domain.model

data class Order(
    val id: String = "",
    val customerName: String,
    val items: List<CartItem>,
    val date: Long,
    val subtotal: Double,
    val taxes: Double,
    val deliveryFee: Double,
    val total: Double
)