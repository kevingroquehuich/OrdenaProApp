package com.roque.data.utils

import com.roque.data.datasource.local.entity.CartItemEntity
import com.roque.data.datasource.local.entity.OrderEntity
import com.roque.data.datasource.local.entity.OrderItemEntity
import com.roque.data.datasource.remote.model.OrderDto
import com.roque.domain.model.CartItem
import com.roque.domain.model.Order

/** CART **/

fun CartItemEntity.toDomain() = CartItem(
    id, productId, title, price, thumbnail, quantity
)

fun CartItem.toEntity() = CartItemEntity(
    id, productId, title, price, thumbnail, quantity
)

/** ORDER **/

fun Order.toEntity() = OrderEntity(
    id = id,
    customerName = customerName,
    date = date,
    subtotal = subtotal,
    taxes = taxes,
    deliveryFee = deliveryFee,
    total = total
)

fun CartItem.toOrderItemEntity(orderId: String) = OrderItemEntity(
    orderId = orderId,
    productId = this.id.toString(),
    title = this.title,
    price = this.price,
    thumbnail = this.thumbnail,
    quantity = quantity
)

fun Order.toDto() = OrderDto(
    id = id,
    customerName = customerName,
    date = date,
    items = items,
    subtotal = subtotal,
    taxes = taxes,
    deliveryFee = deliveryFee,
    total = total
)