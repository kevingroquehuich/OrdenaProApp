package com.roque.data.network.api

import com.roque.data.network.base.BaseResponse
import com.roque.data.network.model.CategoryResponse
import com.roque.data.network.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {

    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int = 100,
        @Query("skip") skip: Int = 0
    ): Response<BaseResponse<List<ProductResponse>>>

    @GET("products/search")
    suspend fun searchProducts(@Query("q") query: String): Response<BaseResponse<List<ProductResponse>>>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<CategoryResponse>>

}