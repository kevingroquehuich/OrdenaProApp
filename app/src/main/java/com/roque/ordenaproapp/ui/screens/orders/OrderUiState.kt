package com.roque.ordenaproapp.ui.screens.orders

sealed class OrderUiState {
    object Idle : OrderUiState()
    object Loading : OrderUiState()
    data class Success(val orderId: String) : OrderUiState()
    data class Error(val message: String) : OrderUiState()
}
