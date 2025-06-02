package com.roque.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.roque.data.datasource.remote.model.CategoryDto
import com.roque.domain.model.Category
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CategoryRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    private val categoryCollection = firestore.collection("categories")

    fun getCategories(): Flow<List<Category>> = callbackFlow {
        val listener = categoryCollection.addSnapshotListener { snapshot, _ ->
            val categoriesDto = snapshot?.documents
                ?.mapNotNull { it.toObject(CategoryDto::class.java) }
                ?: emptyList()
            trySend(categoriesDto.map { it.toDomain() })
        }
        awaitClose { listener.remove() }
    }

}