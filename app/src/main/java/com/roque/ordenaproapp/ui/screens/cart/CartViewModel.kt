package com.roque.ordenaproapp.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roque.domain.model.CartItem
import com.roque.domain.usecase.cart.AddToCartUseCase
import com.roque.domain.usecase.cart.ClearCartUseCase
import com.roque.domain.usecase.cart.GetCartUseCase
import com.roque.domain.usecase.cart.RemoveFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
) : ViewModel() {

    val cartItems = getCartUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun addItem(item: CartItem) = viewModelScope.launch {
        addToCartUseCase(item)
    }

    fun removeItem(item: CartItem) = viewModelScope.launch {
        removeFromCartUseCase(item)
    }

    fun clearCart() = viewModelScope.launch {
        clearCartUseCase()
    }
}
