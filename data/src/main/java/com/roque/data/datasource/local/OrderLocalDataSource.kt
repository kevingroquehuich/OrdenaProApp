package com.roque.data.datasource.local

import com.roque.data.datasource.local.dao.OrderDao
import com.roque.data.datasource.local.entity.OrderEntity
import com.roque.data.datasource.local.entity.OrderItemEntity
import javax.inject.Inject

class OrderLocalDataSource @Inject constructor(
    private val orderDao: OrderDao
) {

    suspend fun addOrder(oederEntity: OrderEntity) = orderDao.insertOrder(oederEntity)

    suspend fun addItems(items: List<OrderItemEntity>) = orderDao.insertOrderItems(items)

}