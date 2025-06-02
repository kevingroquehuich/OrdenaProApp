package com.roque.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roque.data.datasource.local.dao.CartDao
import com.roque.data.datasource.local.dao.OrderDao
import com.roque.data.datasource.local.entity.CartItemEntity
import com.roque.data.datasource.local.entity.OrderEntity
import com.roque.data.datasource.local.entity.OrderItemEntity

@Database(
    entities = [
        CartItemEntity::class,
        OrderEntity::class,
        OrderItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class OrderProDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}