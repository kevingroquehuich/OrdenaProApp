package com.roque.domain.repository

import com.roque.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCart(): Flow<List<CartItem>>
    suspend fun addToCart(item: CartItem)
    suspend fun removeFromCart(item: CartItem)
    suspend fun clearCart()
}
