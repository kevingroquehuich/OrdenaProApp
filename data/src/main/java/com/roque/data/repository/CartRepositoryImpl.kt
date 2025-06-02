package com.roque.data.repository

import com.roque.data.datasource.local.CartLocalDataSource
import com.roque.data.utils.toDomain
import com.roque.data.utils.toEntity
import com.roque.domain.model.CartItem
import com.roque.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartLocalDataSource: CartLocalDataSource
): CartRepository {

    override fun getCart(): Flow<List<CartItem>> =
        cartLocalDataSource.getCartItems().map { list -> list.map { it.toDomain() } }

    override suspend fun addToCart(item: CartItem) {
        val currentItems = cartLocalDataSource.getCartItems().first()
        val existingItem = currentItems.find { it.productId == item.productId }
        if (existingItem != null) {
            val updatedItem = existingItem.copy(
                quantity = existingItem.quantity + item.quantity
            )
            cartLocalDataSource.addOrUpdateItem(updatedItem)
        } else {
            cartLocalDataSource.addOrUpdateItem(item.toEntity())
        }
    }

    override suspend fun removeFromCart(item: CartItem) =
        cartLocalDataSource.deleteItem(item.toEntity())

    override suspend fun clearCart() = cartLocalDataSource.clearCart()
}