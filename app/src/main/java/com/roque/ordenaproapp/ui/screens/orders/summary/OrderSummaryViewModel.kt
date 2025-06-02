package com.roque.ordenaproapp.ui.screens.orders.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.domain.model.CartItem
import com.roque.domain.model.Order
import com.roque.domain.model.OrderPricing
import com.roque.domain.usecase.cart.ClearCartUseCase
import com.roque.domain.usecase.cart.GetCartUseCase
import com.roque.domain.usecase.order.SaveOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class OrderSummaryViewModel @Inject constructor(
    private val saveOrderUseCase: SaveOrderUseCase,
    private val getCartItemsUseCase: GetCartUseCase,
    private val clearCartUseCase: ClearCartUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<OrderSummaryUiState>(OrderSummaryUiState.Idle)
    val uiState: StateFlow<OrderSummaryUiState> = _uiState.asStateFlow()

    private val _pricing = MutableStateFlow(OrderPricing())
    val pricing: StateFlow<OrderPricing> = _pricing

    private var currentCartItems: List<CartItem> = emptyList()

    fun getCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().collect { cartItems ->
                currentCartItems = cartItems
                calculatePricing(cartItems)
            }
        }
    }

    private fun calculatePricing(cartItems: List<CartItem>) {
        val subtotal = cartItems.sumOf { it.price * it.quantity }
        val taxes = subtotal * 0.15
        val deliveryFee = if (cartItems.isNotEmpty()) 3.50 else 0.0
        val total = subtotal + taxes + deliveryFee
        _pricing.value = OrderPricing(subtotal, taxes, deliveryFee, total)
    }


    fun confirmOrder(customerName: String) {
        viewModelScope.launch {
            _uiState.value = OrderSummaryUiState.Loading

            try {
                if (currentCartItems.isEmpty()) {
                    _uiState.value = OrderSummaryUiState.Error("El carrito está vacío")
                    return@launch
                }

                val orderId = UUID.randomUUID().toString()
                val date = System.currentTimeMillis()

                val order = Order(
                    id = orderId,
                    customerName = customerName,
                    date = date,
                    items = currentCartItems,
                    subtotal = _pricing.value.subtotal,
                    taxes = _pricing.value.taxes,
                    deliveryFee = _pricing.value.deliveryFee,
                    total = _pricing.value.total
                )

                saveOrderUseCase(order)
                clearCartUseCase()
                _uiState.value = OrderSummaryUiState.Success(orderId)

            } catch (e: Exception) {
                _uiState.value =
                    OrderSummaryUiState.Error("Error al guardar el pedido: ${e.message}")
            }
        }
    }

    fun resetState() {
        _uiState.value = OrderSummaryUiState.Idle
    }
}