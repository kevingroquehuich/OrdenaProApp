package com.roque.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.roque.data.datasource.remote.model.ProductDto
import com.roque.domain.datasource.remote.ProductRemoteDataSource
import com.roque.domain.model.Product
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : ProductRemoteDataSource {

    private val productCollection = firestore.collection("products")

    override fun getAllProducts(): Flow<List<Product>> = callbackFlow {
        val listener = productCollection.addSnapshotListener { snapshot, _ ->
            val productsDto = snapshot?.documents
                ?.mapNotNull { it.toObject(ProductDto::class.java) }
                ?: emptyList()
            trySend(productsDto.map { it.toDomain() })
        }
        awaitClose { listener.remove() }
    }

    override fun getProductById(id: String): Flow<Product?> = callbackFlow {
        val listener = productCollection.document(id).addSnapshotListener { snapshot, _ ->
            val productDto = snapshot?.toObject(ProductDto::class.java)
            trySend(productDto?.toDomain())
        }
        awaitClose { listener.remove() }
    }

    override fun searchProducts(query: String, category: String?): Flow<List<Product>> {
        return getAllProducts().map { list ->
            list.filter { product ->
                val matchesQuery = product.title.contains(query, ignoreCase = true) ||
                        product.description.contains(query, ignoreCase = true)
                val matchesCategory = category == null || product.category.equals(category, ignoreCase = true)
                matchesQuery && matchesCategory
            }
        }
    }

}