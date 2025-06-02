package com.roque.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val customerName: String,
    val date: Long,
    val subtotal: Double,
    val taxes: Double,
    val deliveryFee: Double,
    val total: Double
)