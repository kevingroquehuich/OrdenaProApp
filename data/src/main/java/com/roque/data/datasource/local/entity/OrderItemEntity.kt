package com.roque.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: String,
    val productId: String,
    val title: String,
    val price: Double,
    val thumbnail: String,
    val quantity: Int
)
