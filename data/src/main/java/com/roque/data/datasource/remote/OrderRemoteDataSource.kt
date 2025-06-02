package com.roque.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.roque.data.datasource.remote.model.OrderDto
import com.roque.domain.model.Order
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val orderCollection = firestore.collection("orders")

    fun uploadOrder(orderDto: OrderDto) {
        orderCollection.document(orderDto.id).set(orderDto)
    }

    fun getAllOrders(): Flow<List<Order>> = callbackFlow {
        val listener = orderCollection.addSnapshotListener { snapshot, _ ->
            val ordersDto = snapshot?.documents
                ?.mapNotNull { it.toObject(OrderDto::class.java) }
                ?: emptyList()
            trySend(ordersDto.map { it.toDomain() }.sortedByDescending { it.date })
        }
        awaitClose { listener.remove() }

    }
}