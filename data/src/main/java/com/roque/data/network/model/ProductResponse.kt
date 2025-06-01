package com.roque.data.network.model

import com.google.gson.annotations.SerializedName
import com.roque.domain.model.Product

class ProductResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("category") val category: String,
    @SerializedName("discountPercentage") val discountPercentage: Double,
    @SerializedName("rating") val rating: Double,
    @SerializedName("stock") val stock: Int
) {

    fun toDomain() = Product(id, title, description, price, thumbnail, category, discountPercentage, rating, stock)

}