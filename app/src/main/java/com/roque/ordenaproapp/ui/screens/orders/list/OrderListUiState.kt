package com.roque.ordenaproapp.ui.screens.orders.list

import com.roque.domain.model.Order

data class OrderListUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val orders: List<Order> = emptyList(),
)