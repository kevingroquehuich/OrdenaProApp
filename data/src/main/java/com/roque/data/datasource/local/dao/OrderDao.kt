package com.roque.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.roque.data.datasource.local.entity.OrderEntity
import com.roque.data.datasource.local.entity.OrderItemEntity

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Insert
    suspend fun insertOrderItems(items: List<OrderItemEntity>)
}