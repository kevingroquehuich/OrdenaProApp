package com.roque.data.repository

import com.roque.data.datasource.local.OrderLocalDataSource
import com.roque.data.datasource.remote.OrderRemoteDataSource
import com.roque.data.utils.toDto
import com.roque.data.utils.toEntity
import com.roque.data.utils.toOrderItemEntity
import com.roque.domain.model.Order
import com.roque.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderRemoteDataSource: OrderRemoteDataSource,
    private val orderLocalDataSource: OrderLocalDataSource
) : OrderRepository {

    override suspend fun saveOrder(order: Order) {
        orderLocalDataSource.addOrder(order.toEntity())
        orderLocalDataSource.addItems(order.items.map { it.toOrderItemEntity(order.id) })
        orderRemoteDataSource.uploadOrder(order.toDto())
    }
}
