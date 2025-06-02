package com.roque.domain.usecase.order

import com.roque.domain.model.Order
import com.roque.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetOrdersUseCase (private val repository: OrderRepository) {

    operator fun invoke(): Flow<List<Order>> = repository.getAllOrders()

}