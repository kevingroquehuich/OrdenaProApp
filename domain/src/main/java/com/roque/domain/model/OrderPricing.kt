package com.roque.domain.model

data class OrderPricing(
    val subtotal: Double = 0.0,
    val taxes: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val total: Double = 0.0
)