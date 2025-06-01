package com.roque.data.network.base

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("products") val data: T?,
    @SerializedName("total") val total: Int?,
    @SerializedName("skip") val skip: Int?,
    @SerializedName("limit") val limit: Int?
)