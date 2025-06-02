package com.roque.domain.repository

import com.roque.domain.model.Order

interface OrderRepository {

    suspend fun saveOrder(order: Order)

}