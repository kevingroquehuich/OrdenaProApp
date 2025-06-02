package com.roque.data.datasource.remote.model

import com.roque.domain.model.CartItem
import com.roque.domain.model.Order

data class OrderDto(
    val id: String = "",
    val customerName: String = "",
    val date: Long = 0L,
    val items: List<CartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val taxes: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val total: Double = 0.0
) {
    fun toDomain(): Order {
        return Order(
            id = id,
            customerName = customerName,
            date = date,
            items = items,
            subtotal = subtotal,
            taxes = taxes,
            deliveryFee = deliveryFee,
            total = total
        )
    }
}
