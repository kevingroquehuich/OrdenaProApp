package com.roque.data.network.model

import com.google.gson.annotations.SerializedName
import com.roque.domain.model.Category

class CategoryResponse(
    @SerializedName("name") val name: String,
    @SerializedName("slug") val slug: String
) {
    fun toDomain() = Category(name, slug)
}