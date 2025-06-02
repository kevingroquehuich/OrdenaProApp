package com.roque.data.datasource.remote.model

import com.roque.domain.model.Category

data class CategoryDto(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val description: String = ""
) {
    fun toDomain(): Category = Category(
        id, name, image, description
    )
}
