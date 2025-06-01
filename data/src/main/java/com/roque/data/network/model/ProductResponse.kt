package com.roque.data.network.model

import com.google.gson.annotations.SerializedName
import com.roque.domain.model.Product

class ProductResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("category") val category: String
) {

    fun toDomain() = Product(id, title, description, price, thumbnail, category)

}