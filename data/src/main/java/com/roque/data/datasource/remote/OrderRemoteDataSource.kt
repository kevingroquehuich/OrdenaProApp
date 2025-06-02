package com.roque.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.roque.data.datasource.remote.model.OrderDto
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val orderCollection = firestore.collection("orders")

    fun uploadOrder(orderDto: OrderDto) {
        orderCollection.document(orderDto.id).set(orderDto)
    }
}