package com.roque.data.datasource.remote.model

import com.google.firebase.firestore.DocumentId
import com.roque.domain.model.Product

data class ProductDto(
    @DocumentId
    val documentId: String = "",

    val title: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val thumbnail: String = "",
    val category: String = "",
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0
) {
    fun toDomain(): Product = Product(
        id = documentId,
        title = title ,
        description = description,
        price = price,
        thumbnail = thumbnail,
        category = category,
        discountPercentage = discountPercentage,
        rating = rating,
        stock = stock
    )
}
