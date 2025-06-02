package com.roque.ordenaproapp.ui.screens.orders.summary

sealed class OrderSummaryUiState {
    object Idle : OrderSummaryUiState()
    object Loading : OrderSummaryUiState()
    data class Success(val orderId: String) : OrderSummaryUiState()
    data class Error(val message: String) : OrderSummaryUiState()
}
