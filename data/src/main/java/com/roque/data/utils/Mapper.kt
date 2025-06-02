package com.roque.data.utils

import com.roque.data.datasource.local.entity.CartItemEntity
import com.roque.domain.model.CartItem

fun CartItemEntity.toDomain() = CartItem(
    id, productId, title, price, thumbnail, quantity
)

fun CartItem.toEntity() = CartItemEntity(
    id, productId, title, price, thumbnail, quantity
)
