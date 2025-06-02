package com.roque.domain.model

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val thumbnail: String,
    val category: String,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int
)