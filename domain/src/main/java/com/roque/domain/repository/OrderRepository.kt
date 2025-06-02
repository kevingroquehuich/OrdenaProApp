package com.roque.domain.repository

import com.roque.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun saveOrder(order: Order)

    fun getAllOrders(): Flow<List<Order>>

}