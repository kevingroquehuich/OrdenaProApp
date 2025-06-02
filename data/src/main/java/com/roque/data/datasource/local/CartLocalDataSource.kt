package com.roque.data.datasource.local

import com.roque.data.datasource.local.dao.CartDao
import com.roque.data.datasource.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartLocalDataSource @Inject constructor(
    private val dao: CartDao
) {
    fun getCartItems(): Flow<List<CartItemEntity>> = dao.getAll()

    suspend fun addOrUpdateItem(item: CartItemEntity) = dao.insert(item)

    suspend fun updateItem(item: CartItemEntity) = dao.update(item)

    suspend fun deleteItem(item: CartItemEntity) = dao.delete(item)

    suspend fun clearCart() = dao.clear()
}