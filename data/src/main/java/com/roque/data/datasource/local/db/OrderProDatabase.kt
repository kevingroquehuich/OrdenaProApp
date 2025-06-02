package com.roque.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roque.data.datasource.local.dao.CartDao
import com.roque.data.datasource.local.entity.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class OrderProDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}