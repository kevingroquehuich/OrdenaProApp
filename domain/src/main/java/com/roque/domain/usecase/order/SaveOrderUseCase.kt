package com.roque.domain.usecase.order

import com.roque.domain.model.Order
import com.roque.domain.repository.OrderRepository


class SaveOrderUseCase(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(order: Order) {
        repository.saveOrder(order)
    }
}
