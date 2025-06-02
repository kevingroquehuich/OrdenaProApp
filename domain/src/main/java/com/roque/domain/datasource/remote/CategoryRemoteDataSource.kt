package com.roque.domain.datasource.remote

import com.roque.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRemoteDataSource {

    fun getCategories(): Flow<List<Category>>
}